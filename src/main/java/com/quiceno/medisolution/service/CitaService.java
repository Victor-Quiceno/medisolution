package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.CitaDTO;
import com.quiceno.medisolution.entity.CitaEntity;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.EstadoCita;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.CitaMapper;
import com.quiceno.medisolution.mapper.EspecialidadMapper;
import com.quiceno.medisolution.mapper.PacienteMapper;
import com.quiceno.medisolution.repository.CitaRepository;
import com.quiceno.medisolution.repository.EspecialidadRepository;
import com.quiceno.medisolution.repository.MedicoRepository;
import com.quiceno.medisolution.repository.PacienteRepository;
import com.quiceno.medisolution.repository.specs.CitaSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CitaService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final CitaRepository citaRepository;

    public CitaService(PacienteRepository pacienteRepository, MedicoRepository medicoRepository,
                       EspecialidadRepository especialidadRepository, CitaRepository citaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.citaRepository = citaRepository;
    }

    public Page<CitaDTO> listarCitasDinamico(EstadoCita estado, String documentoPaciente, String tarjetaMedico, Pageable pageable) {

        // Combinamos los filtros. Si un filtro devuelve null, Spring lo ignora en el SQL final.
        Specification<CitaEntity> spec = Specification
                .where(CitaSpecifications.conEstado(estado))
                .and(CitaSpecifications.conDocumentoPaciente(documentoPaciente))
                .and(CitaSpecifications.conTarjetaMedico(tarjetaMedico));

        // Ejecutamos la consulta dinámica
        return citaRepository.findAll(spec, pageable).map(CitaMapper::toDTO);
    }

    public CitaDTO listarPorId(Long id) {
        CitaEntity citaEncontrada = citaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: La cita que intenta encontrar no existe.")
        );
        return CitaMapper.toDTO(citaEncontrada);
    }

    public CitaDTO guardar(CitaDTO dto) {

        if (dto.getId() != null) {
            dto.setId(null);
        }

        CitaEntity citaGuardar = CitaMapper.toEntity(dto);

        LocalDateTime fechaActual = LocalDateTime.now();


        if (dto.getFecha().isBefore(fechaActual)) {
            throw new IllegalArgumentException("Error: La fecha ingresada no puede ser anterior a la actual");
        } else {
            citaGuardar.setFecha(dto.getFecha());
        }

        // Validar si el médico está disponible a la hora que se intenta agendar la cita
        boolean medicoOcupado = citaRepository.existsByMedicoIdAndFecha(dto.getMedicoId(), dto.getFecha());
        if (medicoOcupado) {
            throw new IllegalArgumentException("Error: El médico para la fecha establecida estará ocupado, por favor seleccione otra fecha.");
        }

        if (dto.getPacienteId() != null) {
            PacienteEntity paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: El paciente que desea agendar no existe."));

            citaGuardar.setPaciente(paciente);
        }

        // Validar si el paciente tiene otra cita programada al mismo tiempo que la que se intenta ingresar
        boolean pacienteOcupado = citaRepository.existsByPacienteIdAndFecha(dto.getPacienteId(), dto.getFecha());
        if (pacienteOcupado) {
            throw new IllegalArgumentException("Error: El paciente ya tiene agendada una cita para la fecha y hora ingresada.");
        }

        if (dto.getMedicoId() != null) {
            MedicoEntity medico = medicoRepository.findById(dto.getMedicoId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: El médico que intenta agendar no existe."));

            citaGuardar.setMedico(medico);
        }

        if (dto.getEspecialidadId() != null) {
            EspecialidadEntity especialidad = especialidadRepository.findById(dto.getEspecialidadId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: La especialidad que intenta ingresar no existe."));

            citaGuardar.setEspecialidad(especialidad);
        }

        citaGuardar.setEstado(EstadoCita.PROGRAMADA);

        CitaEntity citaGuardada = citaRepository.save(citaGuardar);

        return CitaMapper.toDTO(citaGuardada);
    }

    public CitaDTO actualizar(CitaDTO dto) {

        // 1. Buscamos la cita original
        CitaEntity citaActualizar = citaRepository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: La cita que desea modificar no existe."));

        // 2. Validación de paciente (con protección contra nulos)
        if (dto.getPacienteId() != null && !dto.getPacienteId().equals(citaActualizar.getPaciente().getId())) {
            citaActualizar.setPaciente(pacienteRepository.findById(dto.getPacienteId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: El paciente que intenta reasignar a esta cita, no existe.")
            ));
        }

        // 3. Validación de médico (con protección contra nulos)
        if (dto.getMedicoId() != null && !dto.getMedicoId().equals(citaActualizar.getMedico().getId())) {
            citaActualizar.setMedico(medicoRepository.findById(dto.getMedicoId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: El médico que intenta reasignar a esta cita, no existe.")
            ));
        }

        System.out.println("El ID de la cita que llegó al Service es: " + dto.getId());

        // 4. Validamos disponibilidad SOLO si la fecha, el médico o el paciente cambiaron.
        // Usamos el ID actual (dto.getId()) para ignorar la cita que estamos editando.
        boolean medicoOcupado = citaRepository.existsByMedicoIdAndFechaAndIdNot(dto.getMedicoId(), dto.getFecha(), dto.getId());
        boolean pacienteOcupado = citaRepository.existsByPacienteIdAndFechaAndIdNot(dto.getPacienteId(), dto.getFecha(), dto.getId());

        // Usamos OR (||) y Custom Exception
        if (medicoOcupado || pacienteOcupado) {
            // Sobre tu duda de la excepción: DuplicateResourceException es válida, o IllegalArgumentException.
            throw new DuplicateResourceException("Error: El horario que designó para la cita ya está ocupado en la agenda del médico o el paciente.");
        }

        // 5. Actualizamos fecha
        citaActualizar.setFecha(dto.getFecha());

        // 6. Validación de especialidad
        if (dto.getEspecialidadId() != null && !dto.getEspecialidadId().equals(citaActualizar.getEspecialidad().getId())) {
            citaActualizar.setEspecialidad(especialidadRepository.findById(dto.getEspecialidadId()).orElseThrow(
                    () -> new ResourceNotFoundException("Error: La especialidad que intenta reasignar no existe.")
            ));
        }

        // 7. Seteamos los campos simples
        citaActualizar.setMotivo(dto.getMotivo());
        citaActualizar.setEstado(dto.getEstado());

        // 8. Guardamos y retornamos
        CitaEntity citaActualizada = citaRepository.save(citaActualizar);
        return CitaMapper.toDTO(citaActualizada);
    }

    public boolean eliminar(Long id) {

        CitaEntity citaEliminar = citaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: La cita que desea eliminar no existe."));

        citaEliminar.setEstado(EstadoCita.CANCELADA);

        citaRepository.save(citaEliminar);

        return true;
    }
}
