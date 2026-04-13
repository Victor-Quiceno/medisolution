package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity,Long> {

    Optional<PacienteEntity> findByNumeroDocumento(String numeroDocumento);

    boolean existsByEmail(String email);

    Optional<PacienteEntity> findByEmail(String email);

    boolean existsByNumeroDocumento(String numeroDocumento);

    Page<PacienteEntity> findByEstado(Estado estado, Pageable pageable);

    Page<PacienteEntity> findAll(Pageable pageable);
}
