package com.gyl.CrudGyl.exception;

public class ProductoInactivoException extends RuntimeException {

    public ProductoInactivoException(String mensaje) {
        super(mensaje);
    }
}
