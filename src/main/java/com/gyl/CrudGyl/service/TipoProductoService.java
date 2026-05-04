package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.TipoProductoRequestDto;
import com.gyl.CrudGyl.dto.TipoProductoResponseDto;

import java.util.List;

public interface TipoProductoService {

    TipoProductoResponseDto crear(TipoProductoRequestDto dto);

    List<TipoProductoResponseDto> listar();

    TipoProductoResponseDto buscarPorId(Long idTipoProducto);

    TipoProductoResponseDto actualizar(Long idTipoProducto, TipoProductoRequestDto dto);

    void eliminar(Long idTipoProducto);

    List<TipoProductoResponseDto> busquedaPorNombre(String nombreTipoProducto);
}