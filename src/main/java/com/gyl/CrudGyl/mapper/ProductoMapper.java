package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.ProductRequestDto;
import com.gyl.CrudGyl.dto.ProductResponseDto;
import com.gyl.CrudGyl.entity.Producto;

public class ProductoMapper {
    private ProductoMapper() {

    }

    public static Producto toEntity(ProductRequestDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());

        return producto;
    }

    public static ProductResponseDto toResponseDto(Producto producto) {
        return new ProductResponseDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
        );
    }

    public static void updateEntity(Producto producto, ProductRequestDto dto) {
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
    }
}