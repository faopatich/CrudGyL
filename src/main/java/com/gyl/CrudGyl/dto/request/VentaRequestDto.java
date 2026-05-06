package com.gyl.CrudGyl.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentaRequestDto(
        @NotNull(message = "La fecha de venta es obligatoria")
        LocalDate fechaVenta,

        @NotNull(message = "El total de venta es obligatorio")
        @PositiveOrZero(message = "El total no puede ser negativo")
        BigDecimal totalVenta,

        @NotNull(message = "El cliente es obligatorio")
        Long idCliente
) {
}