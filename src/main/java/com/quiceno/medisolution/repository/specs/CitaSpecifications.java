package com.quiceno.medisolution.repository.specs;

import com.quiceno.medisolution.entity.CitaEntity;
import com.quiceno.medisolution.enums.EstadoCita;
import org.springframework.data.jpa.domain.Specification;

/*

El specifications nos sirve para seguir el Specifications Pattern, nos ayuda a poder hacer combinaciones dinámicas de las
consultas SQL que hace el programa debido a una request del cliente. Solo tenemos que crear los bloques de los atributos
principales, ya el patrón de diseño se encarga del resto.

 */

public class CitaSpecifications {

    // Bloque 1: Filtrar por Estado
    public static Specification<CitaEntity> conEstado(EstadoCita estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    // Bloque 2: Filtrar por Documento del Paciente (Hace el JOIN automáticamente)
    public static Specification<CitaEntity> conDocumentoPaciente(String documento) {
        return (root, query, cb) -> {
            if (documento == null || documento.isEmpty()) return null;
            // Viaja a la entidad "paciente" y busca "numeroDocumento"
            return cb.equal(root.join("paciente").get("numeroDocumento"), documento);
        };
    }

    // Bloque 3: Filtrar por Tarjeta del Médico
    public static Specification<CitaEntity> conTarjetaMedico(String tarjeta) {
        return (root, query, cb) -> {
            if (tarjeta == null || tarjeta.isEmpty()) return null;
            return cb.equal(root.join("medico").get("tarjetaProfesional"), tarjeta);
        };
    }
}