package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.MedicoDTO;
import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.MedicoMapper;
import com.quiceno.medisolution.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    public MedicoService(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    public Page<MedicoDTO> listarTodo (Pageable pageable){
        return medicoRepository.findAll(pageable).map(MedicoMapper::toDTO);
    }

    public Page<MedicoDTO> listarActivo (Pageable pageable){
        return medicoRepository.findByEstado(pageable, Estado.ACTIVO).map(MedicoMapper::toDTO);
    }

    public MedicoDTO listarPorId (Long id){
        MedicoEntity encontrado = medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el médico con id: " + id ));

        return MedicoMapper.toDTO(encontrado);
    }

    public MedicoDTO listarPorNumeroDocumento (String numero){
        MedicoEntity encontrado = medicoRepository.findByNumeroDocumento(numero).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró al médico con el número de documento: " + numero));

        return MedicoMapper.toDTO(encontrado);
    }
}
