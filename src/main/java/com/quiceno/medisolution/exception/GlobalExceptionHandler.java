package com.quiceno.medisolution.exception;

import com.quiceno.medisolution.dto.ErrorDetallesDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapamos el error de "No Encontrado" (Código 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetallesDTO> manejarResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetallesDTO error = new ErrorDetallesDTO(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Atrapamos el error de "Horario Cruzado" (Código 409 Conflict o 400 Bad Request)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetallesDTO> manejarDuplicateResourceException(
            DuplicateResourceException exception, WebRequest webRequest) {

        ErrorDetallesDTO error = new ErrorDetallesDTO(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.CONFLICT.value() // CONFLICT (409) es ideal para el cruce de recursos
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 3. Es opcional pero se recomienda para atrapar errores globales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetallesDTO> manejarErroresGlobales(
            Exception exception, WebRequest webRequest) {

        ErrorDetallesDTO error = new ErrorDetallesDTO(
                LocalDateTime.now(),
                "Ha ocurrido un error interno en el servidor",
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> manejarErroresDeValidacion(
            MethodArgumentNotValidException exception, WebRequest webRequest) {

        // Creamos un Map (Diccionario) para guardar qué campo falló y su respectivo mensaje
        Map<String, String> errores = new HashMap<>();

        // Extraemos todos los errores de la excepción y los metemos en nuestro Map
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            errores.put(nombreCampo, mensajeError);
        });

        // En lugar de usar nuestro ErrorDetallesDTO clásico, devolvemos el Map
        // Jackson es tan inteligente que convertirá este Map en un objeto JSON perfecto
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> manejarArgumentosIlegales(
            IllegalArgumentException exception, WebRequest webRequest){
        ErrorDetallesDTO error = new ErrorDetallesDTO(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.CONFLICT.value()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}