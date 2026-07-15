package com.quiceno.medisolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetallesDTO {
    private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;
    private int status;
}