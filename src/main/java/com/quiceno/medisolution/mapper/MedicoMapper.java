package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.EspecialidadDTO;
import com.quiceno.medisolution.dto.MedicoDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.entity.MedicoEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class MedicoMapper {

    public static MedicoDTO toDTO(MedicoEntity entity) {

        MedicoDTO dto = new MedicoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTipoDocumento(entity.getTipoDocumento());
        dto.setNumeroDocumento(entity.getNumeroDocumento());
        dto.setTarjetaProfesional(entity.getTarjetaProfesional());

        if (entity.getEspecialidades() != null){
            Set<EspecialidadDTO> especialidadesDTO = entity.getEspecialidades().stream()
                    .map(EspecialidadMapper::toDTO)
                    .collect(Collectors.toSet());
            dto.setEspecialidades(especialidadesDTO);
        }

        dto.setGenero(entity.getGenero());
        dto.setEmail(entity.getEmail());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setTelefono(entity.getTelefono());
        dto.setArea(entity.getArea());
        dto.setEstado(entity.getEstado());

        return dto;


    }

    public static MedicoEntity toEntity(MedicoDTO dto) {
        if (dto == null) return null;

        MedicoEntity entity = new MedicoEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTipoDocumento(dto.getTipoDocumento());
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setTarjetaProfesional(dto.getTarjetaProfesional());
        entity.setGenero(dto.getGenero());
        entity.setEmail(dto.getEmail());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setTelefono(dto.getTelefono());
        entity.setArea(dto.getArea());
        entity.setEstado(dto.getEstado());

        // NOTA: No mapeamos las especialidades aquí, se hace en el service mapeando con el repo

        return entity;
    }
}
