package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long>,
        JpaSpecificationExecutor<PacienteEntity> {



    boolean existsByNumeroDocumento(String numero);

}
