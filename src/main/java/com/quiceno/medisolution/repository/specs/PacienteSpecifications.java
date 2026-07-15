package com.quiceno.medisolution.repository.specs;

import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.jpa.domain.Specification;

/**
 * Filtros dinámicos para la entidad Paciente (Patrón Specifications).
 * Permite combinar múltiples criterios de búsqueda de forma flexible.
 */
public class PacienteSpecifications {

    public static Specification<PacienteEntity> conEstado (Estado estado){
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<PacienteEntity> conNumeroDocumento (String numeroDocumento){
        return (root, query, cb) -> {
            if (numeroDocumento == null || numeroDocumento.isEmpty()) return null;
            return cb.equal(root.get("numeroDocumento"), numeroDocumento);
        };
    }

    public static Specification<PacienteEntity> conEmail (String email){
        return (root, query, cb) -> {
            if (email == null || email.isEmpty()) return null;
            return cb.equal(root.get("email"), email);
        };
    }

    public static Specification<PacienteEntity> conNombre (String nombre){
        return (root, query, cb) -> {
            if (nombre == null || nombre.isEmpty()) return null;
            return cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<PacienteEntity> conApellido (String apellido){
        return (root, query, cb) -> {
            if (apellido == null || apellido.isEmpty()) return null;
            return cb.like(cb.lower(root.get("apellido")), "%" + apellido.toLowerCase() + "%");
        };
    }
}
