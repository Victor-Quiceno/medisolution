package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.entity.EpsEntity;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.Genero;
import com.quiceno.medisolution.enums.Regimen;
import com.quiceno.medisolution.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    @NotBlank(message = "El nombre es un campo obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es un campo obligatorio")
    private String apellido;

    @NotNull(message = "Tiene que escoger un tipo de documento")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "Tiene que digitar el número de documento")
    private String numeroDocumento;

    @NotNull(message = "Debe escoger un género")
    private Genero genero;

    @Email(message = "El email debe tener un formato válido (ejemplo@dominio.com)")
    @NotBlank(message = "El email es un campo obligatorio.")
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento tiene que ser antes de la actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es un campo obligatorio")
    private String telefono;

    @NotNull(message = "El régimen es un campo obligatorio")
    private Regimen regimen;

    @NotNull(message = "El campo de epsId es obligatorio.")
    private Long epsId;

    private Estado estado;

}
