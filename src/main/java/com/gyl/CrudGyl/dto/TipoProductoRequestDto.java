package com.gyl.CrudGyl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoProductoRequestDto(
        @NotNull(message = "El nombre del tipo de producto es obligatorio")
        @NotBlank(message = "El nombre del tipo de producto no puede estar vacío")
        String nombreTipoProducto
) {
}