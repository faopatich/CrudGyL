package com.gyl.CrudGyl.dto;

public record ClientResponseDto(
        Long idCliente,
        String nombreCliente,
        String apellidoCliente,
        String correoCliente,
        String telefonoCliente,
        String direccionCliente
) {
}
