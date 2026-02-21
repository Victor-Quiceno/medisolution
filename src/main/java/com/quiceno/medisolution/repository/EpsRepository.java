package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.EpsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpsRepository extends JpaRepository<EpsEntity, Long> {
}
