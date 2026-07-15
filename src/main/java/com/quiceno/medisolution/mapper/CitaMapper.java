package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.CitaDTO;
import com.quiceno.medisolution.entity.CitaEntity;

public class CitaMapper {

    public static CitaDTO toDTO (CitaEntity entity){

        if (entity == null) { return null; }

        CitaDTO dto = new CitaDTO();

        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setMotivo(entity.getMotivo());
        dto.setEstado(entity.getEstado());

        dto.setPaciente(PacienteMapper.toDTO(entity.getPaciente()));
        dto.setMedico(MedicoMapper.toDTO(entity.getMedico()));
        dto.setEspecialidad(EspecialidadMapper.toDTO(entity.getEspecialidad()));

        return dto;
    }

    public static CitaEntity toEntity (CitaDTO dto){

        if(dto == null){ return null;}

        CitaEntity entity = new CitaEntity();

        entity.setFecha(dto.getFecha());
        entity.setMotivo(dto.getMotivo());
        entity.setEstado(dto.getEstado());

        // El médico, paciente y especialidad se mapean en el service

        return entity;
    }
}
