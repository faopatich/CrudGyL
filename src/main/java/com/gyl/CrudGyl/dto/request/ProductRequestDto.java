package com.gyl.CrudGyl.dto.request;

import com.gyl.CrudGyl.entity.TipoProducto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequestDto(
        @NotBlank(message = "El nombre no puede estar vacio")
        String nombre,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a cero")
        Double precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El ID del tipo del producto es obligatorio")
        Long idTipoProducto
)

{


}
