package com.quiceno.medisolution.exception;

import com.quiceno.medisolution.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice //Esta anotación le dice a spring que debe vigilar TODOS los controladores
public class GlobalExceptionHandler {

    //Esta anotación dice que si en algún lugar sale un runtimeException, que sea atrapado aquí
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarRuntimeException(RuntimeException ex){

        ErrorDTO error = new ErrorDTO(
                ex.getMessage(), //El mensaje del error
                HttpStatus.NOT_FOUND.value(), //Pone que es un error 404
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
