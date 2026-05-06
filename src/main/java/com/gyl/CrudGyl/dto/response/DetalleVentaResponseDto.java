package com.gyl.CrudGyl.dto.response;

import java.math.BigDecimal;

public record DetalleVentaResponseDto(
        Long idDetalleVenta,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        Long idVenta,
        Long idProducto,
        String nombreProducto
) {
}