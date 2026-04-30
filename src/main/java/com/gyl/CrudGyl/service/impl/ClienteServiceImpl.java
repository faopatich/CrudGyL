package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.ClientResponseDto;
import com.gyl.CrudGyl.dto.ClientRequestDto;
import com.gyl.CrudGyl.repository.ClienteRepository;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.mapper.ClienteMapper;
import com.gyl.CrudGyl.service.ClienteService;
import com.gyl.CrudGyl.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteServiceImpl (ClienteRepository clienteRepository) {this.clienteRepository = clienteRepository;}

    @Override
    public List <ClientResponseDto> busquedaPorNombre (String nombreCliente){
        return clienteRepository.findByNombreCliente(nombreCliente)
                .stream()
                .map(ClienteMapper::toResponseDto)
                .toList();

    }

    @Override
    public List<ClientResponseDto> listar(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toResponseDto)
                .toList();
    }

    @Override
    public ClientResponseDto crear (ClientRequestDto dto){
        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDto(guardado);
    }

    @Override
    public ClientResponseDto buscarPorId(Long idCliente){
        return clienteRepository.findById(idCliente)
                .map(ClienteMapper::toResponseDto)
                .orElseThrow(()-> new RecursoNoEncontradoException(
                        "No se encontró el ID del cliente" + idCliente
                ));
    }

    @Override
    public ClientResponseDto actualizar(Long idCliente, ClientRequestDto dto){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new RecursoNoEncontradoException(
                        "No se encontró el ID del cliente" + idCliente
                ));
        ClienteMapper.updateEntity(cliente, dto);
        Cliente guardado = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDto(guardado);
    }

    @Override
    public void eliminar(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new RecursoNoEncontradoException(
                        "No se encontro el ID del cliente" + idCliente
                ));
        clienteRepository.delete(cliente);
    }


}
