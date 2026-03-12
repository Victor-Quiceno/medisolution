package com.quiceno.medisolution.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String mensaje){
        super(mensaje);
    }
}
