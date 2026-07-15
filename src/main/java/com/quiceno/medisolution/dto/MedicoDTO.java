package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.enums.Areas;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.Genero;
import com.quiceno.medisolution.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "La tarjeta profesional es obligatoria")
    private String tarjetaProfesional;

    // Este atributo solo es usado para recolectar los ID de las especialidades para cuando ser reciba un POST mapear las especialidades hacia el Set de especialidades
    private Set<Long> especialidadesId;

    private Set<EspecialidadDTO> especialidades;

    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotNull(message = "El área es obligatoria")
    private Areas area;

    private Estado estado;

}
