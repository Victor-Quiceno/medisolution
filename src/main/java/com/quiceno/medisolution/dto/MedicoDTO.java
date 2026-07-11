package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.entity.EspecialidadEntity;
import com.quiceno.medisolution.enums.Areas;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.Genero;
import com.quiceno.medisolution.enums.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MedicoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String tarjetaProfesional;

    // Este atributo solo es usado para recolectar los ID de las especialidades para cuando ser reciba un POST mapear las especialidades hacia el Set de especialidades
    private Set<Long> especialidadesId;

    private Set<EspecialidadDTO> especialidades;

    private Genero genero;
    private String email;
    private LocalDate fechaNacimiento;
    private String telefono;
    private Areas area;
    private Estado estado;

}
