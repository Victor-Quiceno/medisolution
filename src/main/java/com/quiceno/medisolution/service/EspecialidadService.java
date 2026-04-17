package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.EspecialidadDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.EspecialidadMapper;
import com.quiceno.medisolution.repository.EspecialidadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository){
        this.especialidadRepository = especialidadRepository;
    }

    public Page<EspecialidadDTO> listarActivas (Pageable pageable){
        Page<EspecialidadEntity> lista = especialidadRepository.findByEstado(Estado.ACTIVO, pageable);
        return lista.map(EspecialidadMapper::toDTO);
    }

    public Page<EspecialidadDTO> listarTodo (Pageable pageable){
        Page<EspecialidadEntity> lista = especialidadRepository.findAll(pageable);
        return lista.map(EspecialidadMapper::toDTO);
    }

    public EspecialidadDTO buscarId (Long id){

        Optional<EspecialidadEntity> encontrado = especialidadRepository.findById(id);

        if (encontrado.isPresent()){
            return EspecialidadMapper.toDTO(encontrado.get());
        } else { throw new ResourceNotFoundException("No se encontró la especialidad con el id " + id);}
    }

    public EspecialidadDTO buscarPorNombre (String nombre){
        EspecialidadEntity encontrado = especialidadRepository.findByNombre(nombre);

        return EspecialidadMapper.toDTO(encontrado);
    }


}
