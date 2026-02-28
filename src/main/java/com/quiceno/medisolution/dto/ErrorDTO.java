package com.quiceno.medisolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private String mensaje;
    private int codigoEstado;
    private LocalDateTime fechaHora;
}
