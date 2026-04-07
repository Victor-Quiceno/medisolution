package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.EpsDTO;
import com.quiceno.medisolution.entity.EpsEntity;

public class EpsMapper {

    public static EpsDTO toDto(EpsEntity eps) {
        return new EpsDTO(
                eps.getId(),
                eps.getNombre(),
                eps.getNit(),
                eps.getDireccion(),
                eps.getTelefono(),
                eps.getEmail(),
                eps.getEstado()
        );
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
