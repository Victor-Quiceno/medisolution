package com.quiceno.medisolution.repository;

import com.quiceno.medisolution.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity, Long>,
                                        JpaSpecificationExecutor<CitaEntity> {

    boolean existsByMedicoIdAndFecha (Long medicoId, LocalDateTime fecha);

    boolean existsByPacienteIdAndFecha (Long pacienteId, LocalDateTime fecha);

    boolean existsByMedicoIdAndFechaAndIdNot(Long MedicoId, LocalDateTime fecha, Long citaId);

    boolean existsByPacienteIdAndFechaAndIdNot(Long PacienteId, LocalDateTime fecha, Long citaId);


}
