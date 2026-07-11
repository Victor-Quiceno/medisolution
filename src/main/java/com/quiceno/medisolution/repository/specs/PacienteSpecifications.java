package com.quiceno.medisolution.repository.specs;

import com.quiceno.medisolution.entity.PacienteEntity;
import com.quiceno.medisolution.enums.Estado;
import org.springframework.data.jpa.domain.Specification;

public class PacienteSpecifications {

    public static Specification<PacienteEntity> conEstado (Estado estado){
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<PacienteEntity> conNumeroDocumento (String numeroDocumento){
        return (root, query, cb) -> {
            if (numeroDocumento == null || numeroDocumento.isEmpty()) return null;

            return cb.equal(root.join("paciente").get("numeroDocumento"), numeroDocumento);
        };
    }

    public static Specification<PacienteEntity> conEmail (String email){
        return (root, query, cb) -> {
            if (email == null || email.isEmpty()) return null;

            return cb.equal(root.join("paciente").get("email"), email);
        };
    }
}
