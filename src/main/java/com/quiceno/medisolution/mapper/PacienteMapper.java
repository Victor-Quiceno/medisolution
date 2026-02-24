package com.quiceno.medisolution.mapper;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.entity.PacienteEntity;

public class PacienteMapper {

    public static PacienteDTO toDTO(PacienteEntity paciente) {
        return new PacienteDTO(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getTipoDocumento(),
                paciente.getNumeroDocumento(),
                paciente.getGenero(),
                paciente.getEmail(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getRegimen(),
                paciente.getEps().getId()
        );
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

        return paciente;
    }


}