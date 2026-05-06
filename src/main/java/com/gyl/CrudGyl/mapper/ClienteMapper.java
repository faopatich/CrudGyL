package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.request.ClientRequestDto;
import com.gyl.CrudGyl.dto.response.ClientResponseDto;
import com.gyl.CrudGyl.entity.Cliente;


public class ClienteMapper {
    private ClienteMapper() {

    }

    public static Cliente toEntity(ClientRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente(dto.nombreCliente());
        cliente.setApellidoCliente(dto.apellidoCliente());
        cliente.setCorreoCliente(dto.correoCliente());
        cliente.setTelefonoCliente(dto.telefonoCliente());
        cliente.setDireccionCliente(dto.direccionCliente());

        return cliente;
    }

    public static ClientResponseDto toResponseDto(Cliente cliente) {
        return new ClientResponseDto(
                cliente.getIdCliente(),
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                cliente.getCorreoCliente(),
                cliente.getTelefonoCliente(),
                cliente.getDireccionCliente()
        );
    }

    public static void updateEntity(Cliente cliente, ClientRequestDto dto) {
        cliente.setNombreCliente(dto.nombreCliente());
        cliente.setApellidoCliente(dto.apellidoCliente());
        cliente.setCorreoCliente(dto.correoCliente());
        cliente.setTelefonoCliente(dto.telefonoCliente());
        cliente.setDireccionCliente(dto.direccionCliente());

    }
}

