package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.ProductRequestDto;
import com.gyl.CrudGyl.dto.ProductResponseDto;

import java.util.List;

public interface ProductoService {

    ProductResponseDto crear (ProductRequestDto dto);

    List<ProductResponseDto> listar();

    ProductResponseDto buscarPorId(Long id);

    ProductResponseDto actualizar (Long id, ProductRequestDto dto);

    void eliminar (Long id);

    List <ProductResponseDto> busquedaPorNombre (String nombre);
}
