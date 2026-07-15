package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity,Long>,
        JpaSpecificationExecutor<PacienteEntity> {

    boolean existsByEmail(String email);

    boolean existsByNumeroDocumento(String numeroDocumento);

}
