package com.quiceno.medisolution.dto;

import java.time.LocalDateTime;

public class ErrorDetallesDTO {
    private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;
    private int status;

    public ErrorDetallesDTO(LocalDateTime timestamp, String mensaje, String detalles, int status) {
        this.timestamp = timestamp;
        this.mensaje = mensaje;
        this.detalles = detalles;
        this.status = status;
    }

    public ErrorDetallesDTO() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}