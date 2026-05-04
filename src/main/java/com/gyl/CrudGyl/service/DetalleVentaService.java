package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.DetalleVentaRequestDto;
import com.gyl.CrudGyl.dto.DetalleVentaResponseDto;

import java.util.List;

public interface DetalleVentaService {

    DetalleVentaResponseDto crear(DetalleVentaRequestDto dto);

    List<DetalleVentaResponseDto> listar();

    DetalleVentaResponseDto buscarPorId(Long idDetalleVenta);

    DetalleVentaResponseDto actualizar(Long idDetalleVenta, DetalleVentaRequestDto dto);

    void eliminar(Long idDetalleVenta);

    List<DetalleVentaResponseDto> buscarPorVenta(Long idVenta);
}