package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {

    Page<EspecialidadEntity> findByEstado(Estado estado, Pageable pageable);

    EspecialidadEntity findByNombre (String nombre);
}
