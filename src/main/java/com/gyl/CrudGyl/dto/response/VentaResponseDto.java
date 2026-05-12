package com.gyl.CrudGyl.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record VentaResponseDto(
        Long idVenta,
        LocalDate fechaVenta,
        BigDecimal totalVenta,
        Long idCliente,
        String nombreCliente,
        String apellidoCliente,
        List<DetalleVentaResponseDto> detalles
) {
}
