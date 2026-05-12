package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.response.DetalleVentaResponseDto;
import com.gyl.CrudGyl.dto.response.VentaResponseDto;
import com.gyl.CrudGyl.entity.Cliente;
import com.gyl.CrudGyl.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VentaMapper {

    private VentaMapper() {}


    public static Venta toEntity(Cliente cliente) {
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setTotalVenta(BigDecimal.ZERO);
        venta.setCliente(cliente);

        return venta;
    }

    public static VentaResponseDto toResponseDto(Venta venta, List<DetalleVentaResponseDto> detalles) {
        return new VentaResponseDto(
                venta.getIdVenta(),
                venta.getFechaVenta(),
                venta.getTotalVenta(),
                venta.getCliente().getIdCliente(),
                venta.getCliente().getNombreCliente(),
                venta.getCliente().getApellidoCliente(),
                detalles
        );
    }
}
