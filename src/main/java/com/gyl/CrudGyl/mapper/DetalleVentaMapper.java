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
        return toEntity(venta, producto, dto.cantidad());
    }

    public static DetalleVenta toEntity(Venta venta, Producto producto, Integer cantidad) {
        BigDecimal precioUnitario = BigDecimal.valueOf(producto.getPrecio());

        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(cantidad)));
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
        updateEntity(detalle, venta, producto, dto.cantidad());
    }

    public static void updateEntity(
            DetalleVenta detalle,
            Venta venta,
            Producto producto,
            Integer cantidad
    ) {
        BigDecimal precioUnitario = BigDecimal.valueOf(producto.getPrecio());

        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(cantidad)));
        detalle.setVenta(venta);
        detalle.setProducto(producto);
    }
}
