package com.quiceno.medisolution.service;

import com.quiceno.medisolution.dto.MedicoDTO;
import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.exception.DuplicateResourceException;
import com.quiceno.medisolution.exception.ResourceNotFoundException;
import com.quiceno.medisolution.mapper.MedicoMapper;
import com.quiceno.medisolution.repository.EspecialidadRepository;
import com.quiceno.medisolution.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;
    public MedicoService(MedicoRepository medicoRepository, EspecialidadRepository especialidadRepository){
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
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

    public MedicoDTO guardar (MedicoDTO dto){

        dto.setId(null);

        if (medicoRepository.existsByNumeroDocumento(dto.getNumeroDocumento())){throw new DuplicateResourceException("Error: El médico que intenta ingresar ya existe");}

        MedicoEntity medicoGuardar = MedicoMapper.toEntity(dto);

        //Mapear la lista de ids de las especialidades en objetos de tipo EspecialidadEntity y luego setearlas en el médico a guardar
        if (dto.getEspecialidadesId() != null && !dto.getEspecialidadesId().isEmpty()){

            Set<EspecialidadEntity> especialidadesEncontradas = dto.getEspecialidadesId().stream()
                    .map(id -> especialidadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: La especialidad con id " + id + " no existe.")))
                    .collect(Collectors.toSet());

            medicoGuardar.setEspecialidades(especialidadesEncontradas);
        }

        //Ponerle un estado por defecto por si no viene con él
        if (dto.getEstado() == null){
            medicoGuardar.setEstado(Estado.ACTIVO);
        }

        MedicoEntity medicoGuardado = medicoRepository.save(medicoGuardar);

        return MedicoMapper.toDTO(medicoGuardado);



    }

}
