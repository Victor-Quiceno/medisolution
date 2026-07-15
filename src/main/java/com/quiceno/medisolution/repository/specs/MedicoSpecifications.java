package com.quiceno.medisolution.repository.specs;

import com.quiceno.medisolution.entity.MedicoEntity;
import com.quiceno.medisolution.enums.Areas;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.jpa.domain.Specification;

/**
 * Filtros dinámicos para la entidad Médico (Patrón Specifications).
 * Permite buscar médicos por múltiples atributos, incluyendo búsquedas parciales (LIKE).
 */
public class MedicoSpecifications {

    public static Specification<MedicoEntity> conEstado(Estado estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<MedicoEntity> conNumeroDocumento(String numeroDocumento) {
        return (root, query, cb) -> {
            if (numeroDocumento == null || numeroDocumento.isEmpty()) return null;
            return cb.equal(root.get("numeroDocumento"), numeroDocumento);
        };
    }

    public static Specification<MedicoEntity> conTarjetaProfesional(String tarjetaProfesional) {
        return (root, query, cb) -> {
            if (tarjetaProfesional == null || tarjetaProfesional.isEmpty()) return null;
            return cb.equal(root.get("tarjetaProfesional"), tarjetaProfesional);
        };
    }

    public static Specification<MedicoEntity> conEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isEmpty()) return null;
            return cb.equal(root.get("email"), email);
        };
    }

    public static Specification<MedicoEntity> conNombre(String nombre) {
        return (root, query, cb) -> {
            if (nombre == null || nombre.isEmpty()) return null;
            return cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<MedicoEntity> conApellido(String apellido) {
        return (root, query, cb) -> {
            if (apellido == null || apellido.isEmpty()) return null;
            return cb.like(cb.lower(root.get("apellido")), "%" + apellido.toLowerCase() + "%");
        };
    }

    public static Specification<MedicoEntity> conArea(Areas area) {
        return (root, query, cb) -> area == null ? null : cb.equal(root.get("area"), area);
    }
}
