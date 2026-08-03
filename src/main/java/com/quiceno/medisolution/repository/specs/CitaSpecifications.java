package com.quiceno.medisolution.repository.specs;

import com.quiceno.medisolution.entity.CitaEntity;
import com.quiceno.medisolution.enums.EstadoCita;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Filtros dinámicos para la entidad Cita (Patrón Specifications).
 * Permite buscar citas por estado, paciente, médico y rangos de fecha.
 */
public class CitaSpecifications {

    // Bloque 1: Filtrar por Estado
    public static Specification<CitaEntity> conEstado(EstadoCita estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    // Bloque 2: Filtrar por Documento del Paciente (Hace el JOIN automáticamente)
    public static Specification<CitaEntity> conDocumentoPaciente(String documentoPaciente) {
        return (root, query, cb) -> {
            if (documentoPaciente == null || documentoPaciente.isEmpty())
                return null;
            return cb.equal(root.join("paciente").get("numeroDocumento"), documentoPaciente);
        };
    }

    // Bloque 3: Filtrar por Tarjeta del Médico
    public static Specification<CitaEntity> conTarjetaMedico(String tarjetaMedico) {
        return (root, query, cb) -> {
            if (tarjetaMedico == null || tarjetaMedico.isEmpty())
                return null;
            return cb.equal(root.join("medico").get("tarjetaProfesional"), tarjetaMedico);
        };
    }

    // Bloque 4: Rango de Fechas (Desde)
    public static Specification<CitaEntity> desdeFecha(LocalDateTime desde) {
        return (root, query, cb) -> desde == null ? null : cb.greaterThanOrEqualTo(root.get("fecha"), desde);
    }

    // Bloque 5: Rango de Fechas (Hasta)
    public static Specification<CitaEntity> hastaFecha(LocalDateTime hasta) {
        return (root, query, cb) -> hasta == null ? null : cb.lessThanOrEqualTo(root.get("fecha"), hasta);
    }

    // Bloque 6: Buscar por palabra clave en el campo de motivo de consulta
    public static Specification<CitaEntity> conMotivo(String palabraMotivo) {
        return (root, query, cb) -> {
            if (palabraMotivo == null || palabraMotivo.isEmpty())
                return null;
            return cb.like(root.get("motivo"), "%" + palabraMotivo + "%");
        };
    }
}