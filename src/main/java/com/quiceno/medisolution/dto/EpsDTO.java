package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.enums.EstadoEps;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EpsDTO {

    private Long id;

    @NotNull(message = "El nombre de la EPS es obligatorio")
    private String nombre;

    @NotNull(message = "El NIT de la EPS es obligatorio")
    private String nit;
    private String direccion;
    private String telefono;

    @NotNull(message = "El email de la EPS es obligatorio")
    private String email;

    private EstadoEps estado;

}
