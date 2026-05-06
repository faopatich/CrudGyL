package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.response.VentaResponseDto;
import com.gyl.CrudGyl.entity.Cliente;
import com.gyl.CrudGyl.entity.Venta;

public class VentaMapper {

    private VentaMapper() {}

    public static Venta toEntity(Cliente cliente) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        return venta;
    }

    public static VentaResponseDto toResponseDto(Venta venta) {
        return new VentaResponseDto(
                venta.getIdVenta(),
                venta.getFechaVenta(),
                venta.getTotalVenta(),
                venta.getCliente().getIdCliente(),
                venta.getCliente().getNombreCliente(),
                venta.getCliente().getApellidoCliente()
        );
    }
}