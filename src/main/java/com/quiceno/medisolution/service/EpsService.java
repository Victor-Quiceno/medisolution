package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.EpsDTO;
import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.enums.EstadoEps;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.EpsMapper;
import com.quiceno.medisolution.repository.EpsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para la gestión de las Entidades Promotoras de Salud (EPS).
 */
@Service
public class EpsService {

    private final EpsRepository epsRepository;

    public EpsService(EpsRepository epsRepository) {
        this.epsRepository = epsRepository;
    }

    public Page<EpsDTO> listarTodas(Pageable pageable) {
        Page<EpsEntity> listaEps = epsRepository.findAll(pageable);

        return listaEps.map(EpsMapper::toDto);
    }

    public Page<EpsDTO> listarActivas(Pageable pageable) {
        Page<EpsEntity> listaEps = epsRepository.findByEstado(EstadoEps.ACTIVO, pageable);
        return listaEps.map(EpsMapper::toDto);
    }

    public EpsDTO buscarPorNit(String nit) {
        EpsEntity eps = epsRepository.findByNit(nit).orElseThrow(
                () -> new ResourceNotFoundException("Error: La eps que busca no ha sido encontrada."));

        return EpsMapper.toDto(eps);
    }

    public EpsDTO guardar(EpsDTO dto) {

        dto.setId(null);
        if (epsRepository.existsByNit(dto.getNit())) {
            throw new DuplicateResourceException("Error: La eps que intenta crear ya existe en el sistema.");
        }
        if (dto.getEstado() == null) {
            dto.setEstado(EstadoEps.ACTIVO);
        }

        EpsEntity eps = EpsMapper.toEntity(dto);
        EpsEntity epsGuardada = epsRepository.save(eps);

        return EpsMapper.toDto(epsGuardada);
    }

    public EpsDTO actualizar(EpsDTO dto) {

        // Validar si la eps existe
        EpsEntity eps = epsRepository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Error: La eps que intenta actualizar no está en el sistema."));

        Optional<EpsEntity> epsNit = epsRepository.findByNit(dto.getNit());
        if (epsNit.isPresent()) {
            EpsEntity duenaNit = epsNit.get();
            if (!duenaNit.getNit().equalsIgnoreCase(eps.getNit())) {
                throw new DuplicateResourceException("Error: El NIT que intenta actualizar ya pertenece a '"
                        + duenaNit.getNombre() + "'");
            }
        }

        eps.setNombre(dto.getNombre());
        eps.setNit(dto.getNit());
        eps.setDireccion(dto.getDireccion());
        eps.setTelefono(dto.getTelefono());
        eps.setEmail(dto.getEmail());
        eps.setEstado(dto.getEstado());
        EpsEntity epsActualizado = epsRepository.save(eps);

        return EpsMapper.toDto(epsActualizado);
    }

    public boolean eliminar(Long id) {
        EpsEntity epsEliminar = epsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error: Eps no encontrada."));
        epsEliminar.setEstado(EstadoEps.INACTIVO);
        epsRepository.save(epsEliminar);

        return true;
    }

}
