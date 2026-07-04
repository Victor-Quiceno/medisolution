package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.CitaDTO;
import com.quiceno.medisolution.entity.CitaEntity;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.EstadoCita;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.CitaMapper;
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

    public Page<CitaDTO> buscarCitasDinamico(EstadoCita estado, String documento, String tarjeta, Pageable pageable) {

        // Combinamos los filtros. Si un filtro devuelve null, Spring lo ignora en el SQL final.
        Specification<CitaEntity> spec = Specification
                .where(CitaSpecifications.conEstado(estado))
                .and(CitaSpecifications.conDocumentoPaciente(documento))
                .and(CitaSpecifications.conTarjetaMedico(tarjeta));

        // Ejecutamos la consulta dinámica
        return citaRepository.findAll(spec, pageable).map(CitaMapper::toDTO);
    }

    public CitaDTO listarPorId (Long id){
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
        if (pacienteOcupado){
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
}
