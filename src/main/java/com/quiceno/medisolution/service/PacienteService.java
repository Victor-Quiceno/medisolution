package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.entity.CitaEntity;
import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.EstadoEps;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.PacienteMapper;
import com.quiceno.medisolution.repository.EpsRepository;
import com.quiceno.medisolution.repository.PacienteRepository;
import com.quiceno.medisolution.repository.specs.PacienteSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PacienteService {

    //Esta es una inyección de dependencias
    private final PacienteRepository pacienteRepository;
    private final EpsRepository epsRepository;

    public PacienteService(PacienteRepository pacienteRepository, EpsRepository epsRepository) {
        this.pacienteRepository = pacienteRepository;
        this.epsRepository = epsRepository;
    }


    public Page<PacienteDTO> listar (Estado estado, String numeroDocumento, String email, Pageable pageable){
        Specification<PacienteEntity> spec = Specification
                .where(PacienteSpecifications.conEstado(estado))
                .and(PacienteSpecifications.conNumeroDocumento(numeroDocumento))
                .and(PacienteSpecifications.conEmail(email));

        return pacienteRepository.findAll(spec, pageable).map(PacienteMapper::toDTO);
    }

    public PacienteDTO listarPorId (Long id){
        PacienteEntity pacienteEncontrado = pacienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: El paciente que intenta encontrar con el id ingresado, no existe."));

        return PacienteMapper.toDTO(pacienteEncontrado);
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

        if (epsEncontrada.getEstado() == EstadoEps.INACTIVO){throw new IllegalArgumentException("No se puede afiliar el paciente a una EPS inactiva");}
        dto.setEstado(Estado.ACTIVO);

        //Mapeo de paciente
        PacienteEntity paciente = PacienteMapper.toEntity(dto);

        //Insertar la eps encontrada al entity y luego guardar en la bd
        paciente.setEps(epsEncontrada);
        PacienteEntity pacienteGuardado = pacienteRepository.save(paciente);

        return PacienteMapper.toDTO(pacienteGuardado);
    }

    public PacienteDTO actualizarPaciente(PacienteDTO dto) {

// 1. Buscamos al paciente original que se quiere actualizar
        PacienteEntity pacienteActual = pacienteRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

// 2. Validar EMAIL
        Specification<PacienteEntity> specEmail = Specification.where(PacienteSpecifications.conEmail(dto.getEmail()));
        Optional<PacienteEntity> pacienteConEseEmail = pacienteRepository.findOne(specEmail);

        if (pacienteConEseEmail.isPresent()) {
            PacienteEntity duenoEmail = pacienteConEseEmail.get();
            // Si encontramos a alguien con ese email, y su ID NO ES el mismo del paciente que estamos actualizando... ¡Robo detectado!
            if (!duenoEmail.getId().equals(pacienteActual.getId())) {
                throw new DuplicateResourceException("Error: El email '" + dto.getEmail() + "' ya está registrado a nombre de otro paciente.");
            }
        }

// 3. Validar NÚMERO DE DOCUMENTO
        Specification<PacienteEntity> specDocumento = Specification.where(PacienteSpecifications.conNumeroDocumento(dto.getNumeroDocumento()));
        Optional<PacienteEntity> pacienteConEseDoc = pacienteRepository.findOne(specDocumento);

        if (pacienteConEseDoc.isPresent()) {
            PacienteEntity duenoDoc = pacienteConEseDoc.get();
            if (!duenoDoc.getId().equals(pacienteActual.getId())) {
                throw new DuplicateResourceException("Error: El documento '" + dto.getNumeroDocumento() + "' ya le pertenece a otro paciente.");
            }
        }


        //Validar si la eps asignada existe
        EpsEntity epsEncontrada = epsRepository.findById(dto.getEpsId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: La eps con id " + dto.getEpsId() + "no existe."));

        //Validar si la eps asignada está inactiva
        if (epsEncontrada.getEstado() == EstadoEps.INACTIVO){throw new IllegalArgumentException("La eps que intenta acutalizar al paciente no puede estar inactiva");}

        pacienteActual.setNombre(dto.getNombre());
        pacienteActual.setApellido(dto.getApellido());
        pacienteActual.setTipoDocumento(dto.getTipoDocumento());
        pacienteActual.setNumeroDocumento(dto.getNumeroDocumento());
        pacienteActual.setGenero(dto.getGenero());
        pacienteActual.setEmail(dto.getEmail());
        pacienteActual.setFechaNacimiento(dto.getFechaNacimiento());
        pacienteActual.setTelefono(dto.getTelefono());
        pacienteActual.setRegimen(dto.getRegimen());

        pacienteActual.setEps(epsEncontrada);
        PacienteEntity pacienteActualizado = pacienteRepository.save(pacienteActual);

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
