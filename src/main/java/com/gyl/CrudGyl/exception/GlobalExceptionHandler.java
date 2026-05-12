package com.gyl.CrudGyl.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiError> manejarRecursoNoEncontrado(RecursoNoEncontradoException exception) {
        return construirRespuesta(HttpStatus.NOT_FOUND, exception.getMessage(), null);
    }

    @ExceptionHandler({
            ProductoInactivoException.class,
            StockInsuficienteException.class,
            VentaSinItemsException.class
    })
    public ResponseEntity<ApiError> manejarReglaDeNegocio(RuntimeException exception) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidaciones(MethodArgumentNotValidException exception) {
        Map<String, String> validaciones = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                validaciones.put(error.getField(), error.getDefaultMessage())
        );

        return construirRespuesta(HttpStatus.BAD_REQUEST, "Datos de entrada invalidos", validaciones);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> manejarIntegridadDeDatos() {
        return construirRespuesta(
                HttpStatus.CONFLICT,
                "No se pudo completar la operacion porque viola una restriccion de datos",
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarErrorGeneral() {
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrio un error inesperado",
                null
        );
    }

    private ResponseEntity<ApiError> construirRespuesta(
            HttpStatus status,
            String mensaje,
            Map<String, String> validaciones
    ) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        mensaje,
                        validaciones
                ));
    }
}
