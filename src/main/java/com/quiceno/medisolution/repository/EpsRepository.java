package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.enums.EstadoEps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<EpsEntity, Long> {
    Optional<EpsEntity> findByNit(String nit);
    Boolean existsByNit(String nit);
    List<EpsEntity> findByEstado (EstadoEps estado);
}
