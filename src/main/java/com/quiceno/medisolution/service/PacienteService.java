package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.PacienteMapper;
import com.quiceno.medisolution.repository.EpsRepository;
import com.quiceno.medisolution.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    //Esta es una inyección de dependencias
    private final PacienteRepository pacienteRepository;
    private final EpsRepository epsRepository;

    public PacienteService(PacienteRepository pacienteRepository, EpsRepository epsRepository) {
        this.pacienteRepository = pacienteRepository;
        this.epsRepository = epsRepository;
    }

    public List<PacienteDTO> listarTodo() {
        return pacienteRepository.findAll().stream().map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PacienteDTO> listarActivo(){
        return pacienteRepository.findByEstado(Estado.ACTIVO).stream().map(PacienteMapper::toDTO).collect(Collectors.toList());
    }

    public PacienteDTO buscarPorNumeroDocumento(String numeroDocumento) {
        PacienteEntity paciente = pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("El paciente con número de documento: " + numeroDocumento +
                        ", no ha sido encontrado."));

        return PacienteMapper.toDTO(paciente);
    }

    public PacienteDTO guardarPaciente(PacienteDTO dto) {

        dto.setId(null);
        if (pacienteRepository.existsByNumeroDocumento(dto.getNumeroDocumento())){ throw new DuplicateResourceException("Error: El paciente que intenta crear ya existe en el sistema");}

        if (pacienteRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("El email " + dto.getEmail() + ", ya está siendo utilizado por otro paciente.");
        }

        //Aquí se verifica si la eps enviada por el frontend existe
        EpsEntity epsEncontrada = epsRepository.findById(dto.getEpsId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: La eps con id " + dto.getEpsId() + "no existe."));

        //Mapeo de paciente
        PacienteEntity paciente = PacienteMapper.toEntity(dto);

        //Insertar la eps encontrada al entity y luego guardar en la bd
        paciente.setEps(epsEncontrada);
        PacienteEntity pacienteGuardado = pacienteRepository.save(paciente);

        return PacienteMapper.toDTO(pacienteGuardado);
    }

    public PacienteDTO actualizarPaciente(PacienteDTO dto) {

        //Validar si el paciente que se quiere actualizar existe
        PacienteEntity pacienteEncontrado = pacienteRepository.findByNumeroDocumento(dto.getNumeroDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("Error: El paciente que desea actualizar no existe."));

        //Validar si el email nuevo ya existe
        Optional<PacienteEntity> pacienteEmail = pacienteRepository.findByEmail(dto.getEmail());
        if (pacienteEmail.isPresent()) {
            PacienteEntity duenoEmail = pacienteEmail.get();

            if (!duenoEmail.getNumeroDocumento().equalsIgnoreCase(dto.getNumeroDocumento())) {
                throw new DuplicateResourceException("Error: El email que intenta actualizar, " +
                        "ya existe, pertenece al paciente con CC: " + duenoEmail.getNumeroDocumento());
            }
        }

        //Validar si la eps asignada existe
        EpsEntity epsEncontrada = epsRepository.findById(dto.getEpsId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: La eps con id " + dto.getEpsId() + "no existe."));

        pacienteEncontrado.setNombre(dto.getNombre());
        pacienteEncontrado.setApellido(dto.getApellido());
        pacienteEncontrado.setTipoDocumento(dto.getTipoDocumento());
        pacienteEncontrado.setNumeroDocumento(dto.getNumeroDocumento());
        pacienteEncontrado.setGenero(dto.getGenero());
        pacienteEncontrado.setEmail(dto.getEmail());
        pacienteEncontrado.setFechaNacimiento(dto.getFechaNacimiento());
        pacienteEncontrado.setTelefono(dto.getTelefono());
        pacienteEncontrado.setRegimen(dto.getRegimen());

        pacienteEncontrado.setEps(epsEncontrada);
        PacienteEntity pacienteActualizado = pacienteRepository.save(pacienteEncontrado);

        return PacienteMapper.toDTO(pacienteActualizado);

    }

    public boolean eliminar (Long id){
        PacienteEntity paciente = pacienteRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("Error: Paciente no encontrado"));
        paciente.setEstado(Estado.INACTIVO);
        pacienteRepository.save(paciente);

        return true;
    }

}
