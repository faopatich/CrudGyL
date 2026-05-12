package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.request.VentaRequestDto;
import com.gyl.CrudGyl.dto.response.VentaResponseDto;

import java.util.List;

public interface VentaService {

    VentaResponseDto crear(VentaRequestDto dto);

    List<VentaResponseDto> listar();

    VentaResponseDto buscarPorId(Long idVenta);

    List<VentaResponseDto> buscarPorCliente(Long idCliente);
}