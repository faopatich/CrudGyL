package com.gyl.CrudGyl.controller;


import com.gyl.CrudGyl.dto.request.ClientRequestDto;
import com.gyl.CrudGyl.dto.response.ClientResponseDto;
import com.gyl.CrudGyl.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto crear(@Valid @RequestBody ClientRequestDto dto) {
        return clienteService.crear(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDto> listar() {
        return clienteService.listar();
    }

    @GetMapping("id/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponseDto buscarPorId(@PathVariable Long idCliente) {
        return clienteService.buscarPorId(idCliente);
    }

    @PutMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ClientResponseDto actualizar(@PathVariable Long idCliente, @Valid @RequestBody ClientRequestDto dto) {
        return clienteService.actualizar(idCliente, dto);
    }

    @DeleteMapping("/id/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idCliente) {
        clienteService.eliminar(idCliente);
    }

    @GetMapping("/nombre/{nombreCliente}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDto> busquedaPorNombre(@Valid @PathVariable String nombreCliente) {
        return clienteService.busquedaPorNombre(nombreCliente);
    }
}
