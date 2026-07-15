package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.MedicoDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.Areas;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.MedicoMapper;
import com.quiceno.medisolution.repository.EspecialidadRepository;
import com.quiceno.medisolution.repository.MedicoRepository;
import com.quiceno.medisolution.repository.specs.MedicoSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para la gestión de Médicos.
 * Incluye asignación de especialidades y validaciones de unicidad.
 */
@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    /**
     * Obtiene una lista paginada de médicos filtrada dinámicamente.
     */
    public Page<MedicoDTO> listarDinamico(Estado estado, String numeroDocumento, String tarjetaProfesional,
            String email, String nombre, String apellido, Areas area, Pageable pageable) {
        Specification<MedicoEntity> spec = Specification
                .where(MedicoSpecifications.conEstado(estado))
                .and(MedicoSpecifications.conNumeroDocumento(numeroDocumento))
                .and(MedicoSpecifications.conTarjetaProfesional(tarjetaProfesional))
                .and(MedicoSpecifications.conEmail(email))
                .and(MedicoSpecifications.conNombre(nombre))
                .and(MedicoSpecifications.conApellido(apellido))
                .and(MedicoSpecifications.conArea(area));
        return medicoRepository.findAll(spec, pageable).map(MedicoMapper::toDTO);
    }

    public MedicoDTO listarPorId(Long id) {
        MedicoEntity encontrado = medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el médico con id: " + id));

        return MedicoMapper.toDTO(encontrado);
    }

    public MedicoDTO listarPorNumeroDocumento(String numero) {
        Specification<MedicoEntity> spec = Specification.where(MedicoSpecifications.conNumeroDocumento(numero));
        MedicoEntity encontrado = medicoRepository.findOne(spec).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró al médico con el número de documento: " + numero));

        return MedicoMapper.toDTO(encontrado);
    }

    public MedicoDTO guardar(MedicoDTO dto) {

        dto.setId(null);

        if (medicoRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
            throw new DuplicateResourceException("Error: El médico que intenta ingresar ya existe");
        }

        MedicoEntity medicoGuardar = MedicoMapper.toEntity(dto);

        // Mapear la lista de ids de las especialidades en objetos de tipo
        // EspecialidadEntity y luego setearlas en el médico a guardar
        if (dto.getEspecialidadesId() != null && !dto.getEspecialidadesId().isEmpty()) {

            Set<EspecialidadEntity> especialidadesEncontradas = dto.getEspecialidadesId().stream()
                    .map(id -> especialidadRepository.findById(id).orElseThrow(
                            () -> new ResourceNotFoundException("Error: La especialidad con id " + id + " no existe.")))
                    .collect(Collectors.toSet());

            medicoGuardar.setEspecialidades(especialidadesEncontradas);
        }

        // Ponerle un estado por defecto por si no viene con él
        if (dto.getEstado() == null) {
            medicoGuardar.setEstado(Estado.ACTIVO);
        }

        MedicoEntity medicoGuardado = medicoRepository.save(medicoGuardar);

        return MedicoMapper.toDTO(medicoGuardado);
    }

    public MedicoDTO actualizar(MedicoDTO dto) {

        // 1. Buscamos SIEMPRE por el ID interno de la BD, es el único dato en el que
        // podemos confiar.
        MedicoEntity medicoActualizar = medicoRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: El médico que intenta actualizar no existe."));

        // 2. Validación de Documento: Si lo está cambiando, verificar que el nuevo no
        // le pertenezca a otro
        if (!medicoActualizar.getNumeroDocumento().equals(dto.getNumeroDocumento())) {
            if (medicoRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
                throw new DuplicateResourceException(
                        "Error: El nuevo número de documento ya le pertenece a otro médico.");
            }
        }

        // 3. Validación de Tarjeta Profesional usando Specifications
        Specification<MedicoEntity> specTarjeta = Specification
                .where(MedicoSpecifications.conTarjetaProfesional(dto.getTarjetaProfesional()));
        Optional<MedicoEntity> medicoTarjeta = medicoRepository.findOne(specTarjeta);
        if (medicoTarjeta.isPresent() && !medicoTarjeta.get().getId().equals(medicoActualizar.getId())) {
            throw new DuplicateResourceException("Error: La tarjeta profesional ya le pertenece a otro médico.");
        }

        // 4. Validación de Email usando Specifications
        Specification<MedicoEntity> specEmail = Specification.where(MedicoSpecifications.conEmail(dto.getEmail()));
        Optional<MedicoEntity> medicoEmail = medicoRepository.findOne(specEmail);
        if (medicoEmail.isPresent() && !medicoEmail.get().getId().equals(medicoActualizar.getId())) {
            throw new DuplicateResourceException(
                    "Error: El email que intenta actualizar ya le pertenece a otro médico.");
        }

        // 5. Actualización de datos básicos
        medicoActualizar.setNombre(dto.getNombre());
        medicoActualizar.setApellido(dto.getApellido());
        medicoActualizar.setTipoDocumento(dto.getTipoDocumento());
        medicoActualizar.setNumeroDocumento(dto.getNumeroDocumento());
        medicoActualizar.setTarjetaProfesional(dto.getTarjetaProfesional());
        medicoActualizar.setGenero(dto.getGenero());
        medicoActualizar.setEmail(dto.getEmail());
        medicoActualizar.setFechaNacimiento(dto.getFechaNacimiento());
        medicoActualizar.setTelefono(dto.getTelefono());
        medicoActualizar.setArea(dto.getArea());
        medicoActualizar.setEstado(dto.getEstado());

        // 6. Especialidades, si mandan una lista vacía, limpiamos las especialidades
        if (dto.getEspecialidadesId() != null) {
            if (dto.getEspecialidadesId().isEmpty()) {
                medicoActualizar.getEspecialidades().clear(); // El médico decidió quitarse todas las especialidades
            } else {
                Set<EspecialidadEntity> especialidades = dto.getEspecialidadesId().stream()
                        .map(id -> especialidadRepository.findById(id).orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Error: La especialidad con id " + id + " no existe.")))
                        .collect(Collectors.toSet());
                medicoActualizar.setEspecialidades(especialidades);
            }
        }

        MedicoEntity medicoActualizado = medicoRepository.save(medicoActualizar);
        return MedicoMapper.toDTO(medicoActualizado);
    }

    public boolean eliminar(Long id) {
        MedicoEntity medicoEliminar = medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: El médico a eliminar no existe"));

        medicoEliminar.setEstado(Estado.INACTIVO);
        medicoRepository.save(medicoEliminar);

        return true;
    }

}
