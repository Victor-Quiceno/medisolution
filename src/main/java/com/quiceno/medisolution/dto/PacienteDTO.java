package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.enums.Genero;
import com.quiceno.medisolution.enums.Regimen;
import com.quiceno.medisolution.enums.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Genero genero;
    private String email;
    private LocalDate fechaNacimiento;
    private String telefono;
    private Regimen regimen;
    private Long epsId;

}
