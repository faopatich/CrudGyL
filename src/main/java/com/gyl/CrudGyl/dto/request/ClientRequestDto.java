package com.gyl.CrudGyl.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequestDto(
        @NotNull(message = "El nombre es obligatorio")
        @NotBlank(message = "El nombre no puede estar vacio")
        String nombreCliente,

        @NotNull(message = "El apellido es obligatorio")
        @NotBlank(message = "El apellido no puede estar vacio")
        String apellidoCliente,

        @Email(message = "El corre debe tener un formato valido")
        @NotBlank(message = "El correo no puede estar vacio")
        String correoCliente,

        @NotBlank(message = "El telefono no puede estar vacio")
        String telefonoCliente,

        @NotBlank(message = "La direccion no puede estar vacia")
        String direccionCliente


) {


}

