package com.quiceno.medisolution.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.enums.Estado;

import io.micrometer.common.util.StringUtils;

public class EspecialidadSpecifications {

    public static Specification<EspecialidadEntity> conEstado(Estado estado) {

        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<EspecialidadEntity> conNombre(String nombre) {
        return (root, query, cb) -> StringUtils.isBlank(nombre) ? null
                : cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

}
