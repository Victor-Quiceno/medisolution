package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.EspecialidadDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.EspecialidadMapper;
import com.quiceno.medisolution.repository.EspecialidadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public Page<EspecialidadDTO> listarActivas(Pageable pageable) {
        Page<EspecialidadEntity> lista = especialidadRepository.findByEstado(Estado.ACTIVO, pageable);
        return lista.map(EspecialidadMapper::toDTO);
    }

    public Page<EspecialidadDTO> listarTodo(Pageable pageable) {
        Page<EspecialidadEntity> lista = especialidadRepository.findAll(pageable);
        return lista.map(EspecialidadMapper::toDTO);
    }

    public EspecialidadDTO buscarId(Long id) {

        Optional<EspecialidadEntity> encontrado = especialidadRepository.findById(id);

        if (encontrado.isPresent()) {
            return EspecialidadMapper.toDTO(encontrado.get());
        } else {
            throw new ResourceNotFoundException("No se encontró la especialidad con el id " + id);
        }
    }

    public EspecialidadDTO buscarPorNombre(String nombre) {
        EspecialidadEntity encontrado = especialidadRepository.findByNombre(nombre).orElseThrow(
                () -> new ResourceNotFoundException("No se ha encontrado la especialidad ingresada"));

        return EspecialidadMapper.toDTO(encontrado);
    }

    public EspecialidadDTO guardar(EspecialidadDTO dto) {

        dto.setId(null);

        if (especialidadRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("La especialidad que intenta guardar ya existe");
        }
        if (dto.getEstado() == null) {
            dto.setEstado(Estado.ACTIVO);
        }

        EspecialidadEntity especialidad = EspecialidadMapper.toEntity(dto);
        EspecialidadEntity especialidadGuardada = especialidadRepository.save(especialidad);

        return EspecialidadMapper.toDTO(especialidadGuardada);

    }

    public EspecialidadDTO actualizar(EspecialidadDTO dto) {

        //Validar si la especialidad a actualizar existe o no
        EspecialidadEntity encontrada = especialidadRepository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("La especialidad que intenta actualizar no existe."));

        //Validar si el nombre actualizado ya le pertenece a otra especialidad
        Optional<EspecialidadEntity> especialidadExistente = especialidadRepository.findByNombre(dto.getNombre());
        if (especialidadExistente.isPresent() && !especialidadExistente.get().getId().equals(dto.getId())) {
            throw new DuplicateResourceException("Error: El nombre de la especialidad ya está en uso.");
        }


        encontrada.setNombre(dto.getNombre());
        encontrada.setDescripcion(dto.getDescripcion());
        encontrada.setEstado(dto.getEstado());


        EspecialidadEntity guardada = especialidadRepository.save(encontrada);
        return EspecialidadMapper.toDTO(guardada);
    }

    public boolean eliminar(Long id) {
        EspecialidadEntity encontrada = especialidadRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La especialidad con el id " + id + " no existe en el sistema."));

        encontrada.setEstado(Estado.INACTIVO);

        EspecialidadEntity guardada = especialidadRepository.save(encontrada);

        return true;
    }


}
