package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity,Long> {

    Optional<PacienteEntity> findByNumeroDocumento(String numeroDocumento);

    boolean existsByEmail(String email);
}
