package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.EspecialidadDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;

public class EspecialidadMapper {

    public static EspecialidadDTO toDTO(EspecialidadEntity entity) {

        if (entity == null){return null;}
        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEstado(entity.getEstado());

        return dto;
    }

    public static EspecialidadEntity toEntity(EspecialidadDTO dto) {

        EspecialidadEntity entity = new EspecialidadEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.getEstado());

        return entity;
    }
}
