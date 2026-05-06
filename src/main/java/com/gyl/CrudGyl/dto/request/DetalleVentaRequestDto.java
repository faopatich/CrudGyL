package com.gyl.CrudGyl.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DetalleVentaRequestDto(
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a cero")
        Integer cantidad,

        @NotNull(message = "El precio unitario es obligatorio")
        @PositiveOrZero(message = "El precio unitario no puede ser negativo")
        BigDecimal precioUnitario,

        @NotNull(message = "El ID de venta es obligatorio")
        Long idVenta,

        @NotNull(message = "El ID de producto es obligatorio")
        Long idProducto
) {
}