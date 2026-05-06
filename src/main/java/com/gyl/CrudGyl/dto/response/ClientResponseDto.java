package com.gyl.CrudGyl.dto.response;

public record ClientResponseDto(
        Long idCliente,
        String nombreCliente,
        String apellidoCliente,
        String correoCliente,
        String telefonoCliente,
        String direccionCliente
) {
}
