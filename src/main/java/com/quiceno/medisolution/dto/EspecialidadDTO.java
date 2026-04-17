package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.enums.Estado;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadDTO {

    private Long id;

    @NotBlank
    private String nombre;

    @Length(max = 255)
    private String descripcion;

    private Estado estado;

}
