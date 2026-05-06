package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.request.DetalleVentaRequestDto;
import com.gyl.CrudGyl.dto.response.DetalleVentaResponseDto;
import com.gyl.CrudGyl.entity.DetalleVenta;
import com.gyl.CrudGyl.entity.Producto;
import com.gyl.CrudGyl.entity.Venta;

import java.math.BigDecimal;

public class DetalleVentaMapper {

    private DetalleVentaMapper() {}

    public static DetalleVenta toEntity(
            DetalleVentaRequestDto dto,
            Venta venta,
            Producto producto
    ) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(dto.cantidad());
        detalle.setPrecioUnitario(dto.precioUnitario());
        detalle.setSubtotal(dto.precioUnitario().multiply(BigDecimal.valueOf(dto.cantidad())));
        detalle.setVenta(venta);
        detalle.setProducto(producto);

        return detalle;
    }

    public static DetalleVentaResponseDto toResponseDto(DetalleVenta detalle) {
        return new DetalleVentaResponseDto(
                detalle.getIdDetalleVenta(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal(),
                detalle.getVenta().getIdVenta(),
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre()
        );
    }

    public static void updateEntity(
            DetalleVenta detalle,
            DetalleVentaRequestDto dto,
            Venta venta,
            Producto producto
    ) {
        detalle.setCantidad(dto.cantidad());
        detalle.setPrecioUnitario(dto.precioUnitario());
        detalle.setSubtotal(dto.precioUnitario().multiply(BigDecimal.valueOf(dto.cantidad())));
        detalle.setVenta(venta);
        detalle.setProducto(producto);
    }
}