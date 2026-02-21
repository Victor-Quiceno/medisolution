package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity, Long> {
}
