package com.quiceno.medisolution.exception;

import com.quiceno.medisolution.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //Esta anotación le dice a spring que debe vigilar TODOS los controladores
public class GlobalExceptionHandler {

    //Aquí estoy usando una excepción personalizada atrapada por el ExceptionHandler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarNotFoudException(ResourceNotFoundException ex) {

        ErrorDTO error = new ErrorDTO(
                ex.getMessage(), //El mensaje del error
                HttpStatus.NOT_FOUND.value(), //Pone que es un error 404
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Este metodo captura cuando se está intentando hacer una transacción con un elemento duplicado
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDTO> manejarDuplicateResourceException(DuplicateResourceException ex) {
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //Método para capturar las excepciones disparadas por el @Valid (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex){

        Map<String, String>errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();
            String mensaje = error.getDefaultMessage();

            errores.put(campo, mensaje);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}
