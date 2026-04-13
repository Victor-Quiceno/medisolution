package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.EpsDTO;
import com.quiceno.medisolution.entity.EpsEntity;

public class EpsMapper {

    public static EpsDTO toDto(EpsEntity eps) {

        EpsDTO dto = new EpsDTO();

        dto.setId(eps.getId());
        dto.setNombre(eps.getNombre());
        dto.setNit(eps.getNit());
        dto.setDireccion(eps.getDireccion());
        dto.setTelefono(eps.getTelefono());
        dto.setEmail(eps.getEmail());
        dto.setEstado(eps.getEstado());
        return dto;
    }

    public static EpsEntity toEntity(EpsDTO dto) {

        EpsEntity eps = new EpsEntity();
        eps.setId(dto.getId());
        eps.setNombre(dto.getNombre());
        eps.setNit(dto.getNit());
        eps.setDireccion(dto.getDireccion());
        eps.setTelefono(dto.getTelefono());
        eps.setEmail(dto.getEmail());
        eps.setEstado(dto.getEstado());

        return eps;
    }
}
