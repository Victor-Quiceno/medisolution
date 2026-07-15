package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.entity.PacienteEntity;

public class PacienteMapper {

    public static PacienteDTO toDTO(PacienteEntity paciente) {
        if (paciente == null) {
            return null;
        }

        PacienteDTO dto = new PacienteDTO();

        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setTipoDocumento(paciente.getTipoDocumento());
        dto.setNumeroDocumento(paciente.getNumeroDocumento());
        dto.setGenero(paciente.getGenero());
        dto.setEmail(paciente.getEmail());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setTelefono(paciente.getTelefono());
        dto.setRegimen(paciente.getRegimen());

        if (paciente.getEps() != null) {
            dto.setEpsId(paciente.getEps().getId());
            dto.setEps(EpsMapper.toDto(paciente.getEps()));
        }
        dto.setEstado(paciente.getEstado());

        return dto;
    }

    public static PacienteEntity toEntity(PacienteDTO dto) {
        PacienteEntity paciente = new PacienteEntity();
        paciente.setId(dto.getId());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setTipoDocumento(dto.getTipoDocumento());
        paciente.setNumeroDocumento(dto.getNumeroDocumento());
        paciente.setGenero(dto.getGenero());
        paciente.setEmail(dto.getEmail());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setTelefono(dto.getTelefono());
        paciente.setRegimen(dto.getRegimen());
        paciente.setEstado(dto.getEstado());

        return paciente;
    }

}