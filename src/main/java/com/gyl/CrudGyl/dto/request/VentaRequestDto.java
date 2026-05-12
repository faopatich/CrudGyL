package com.gyl.CrudGyl.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VentaRequestDto(
        @NotNull(message = "El cliente es obligatorio")
        Long idCliente,

        @Valid
        @NotEmpty(message = "La venta debe tener al menos un producto")
        List<ItemVentaRequestDto> items
) {
}
