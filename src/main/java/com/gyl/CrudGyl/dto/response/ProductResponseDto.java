package com.gyl.CrudGyl.dto.response;


public record ProductResponseDto(
        Long id,
        String nombre,
        Double precio,
        Integer stock

) {
}
