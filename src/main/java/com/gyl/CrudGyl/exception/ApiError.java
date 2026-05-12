package com.gyl.CrudGyl.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        Map<String, String> validaciones
) {
}
