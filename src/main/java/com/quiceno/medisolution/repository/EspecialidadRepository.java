package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {
}
