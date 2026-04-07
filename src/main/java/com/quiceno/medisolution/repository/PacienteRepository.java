package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity,Long> {

    Optional<PacienteEntity> findByNumeroDocumento(String numeroDocumento);

    boolean existsByEmail(String email);

    Optional<PacienteEntity> findByEmail(String email);

    boolean existsByNumeroDocumento(String numeroDocumento);

    List<PacienteEntity> findByEstado(Estado estado);
}
