package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.VentaRequestDto;
import com.gyl.CrudGyl.dto.VentaResponseDto;

import java.util.List;

public interface VentaService {

    VentaResponseDto crear(VentaRequestDto dto);

    List<VentaResponseDto> listar();

    VentaResponseDto buscarPorId(Long idVenta);

    VentaResponseDto actualizar(Long idVenta, VentaRequestDto dto);

    void eliminar(Long idVenta);

    List<VentaResponseDto> buscarPorCliente(Long idCliente);
}