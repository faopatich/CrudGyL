package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.ClientRequestDto;
import com.gyl.CrudGyl.dto.ClientResponseDto;

import java.util.List;

public interface ClienteService {
    ClientResponseDto crear (ClientRequestDto dto);

    List<ClientResponseDto> listar();

    ClientResponseDto buscarPorId(Long idCliente);

    ClientResponseDto actualizar (Long idCliente, ClientRequestDto dto);

    void eliminar (Long idCliente);

    List <ClientResponseDto> busquedaPorNombre (String nombreCliente);

}
