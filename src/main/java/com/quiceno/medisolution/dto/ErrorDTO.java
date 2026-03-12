package com.quiceno.medisolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Este DTO tiene la función de contener cualquier error que surga en la aplicación y ser pasado al cliente
// Al hacer esto estamos siendo organizados y no le estamos pasando simplemente texto plano

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private String mensaje;
    private int codigoEstado;
    private LocalDateTime fechaHora;
}
