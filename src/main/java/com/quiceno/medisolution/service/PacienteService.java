package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.mapper.PacienteMapper;
import com.quiceno.medisolution.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    //Esta es una inyección de dependencias
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteDTO> listar() {
        return pacienteRepository.findAll().stream().map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO buscaId(String numeroDocumento) {
        PacienteEntity paciente = pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Paciente con número de documento: " + numeroDocumento + "no encontrado"));
        return PacienteMapper.toDTO(paciente);
    }

}
