package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.enums.EstadoEps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<EpsEntity, Long> {
    Optional<EpsEntity> findByNit(String nit);
    Boolean existsByNit(String nit);
    Page<EpsEntity> findByEstado (EstadoEps estado, Pageable pageable);
}
