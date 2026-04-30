package com.gyl.CrudGyl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequestDto(
        @NotNull(message = "El nombre es obligatorio")
        @NotBlank(message = "El nombre no puede estar vacio")
        String nombreCliente,

        @NotNull(message = "El apellido es obligatorio")
        @NotBlank(message = "El apellido no puede estar vacio")
        String apellidoCliente,

        @NotBlank(message = "El correo no puede estar vacio")
        String correoCliente,

        @NotBlank(message = "El telefono no puede estar vacio")
        String telefonoCliente,

        @NotBlank(message = "La direccion no puede estar vacia")
        String direccionCliente


) {


}

