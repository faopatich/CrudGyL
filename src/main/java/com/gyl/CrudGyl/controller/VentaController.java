package com.gyl.CrudGyl.controller;

import com.gyl.CrudGyl.dto.request.VentaRequestDto;
import com.gyl.CrudGyl.dto.response.VentaResponseDto;
import com.gyl.CrudGyl.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponseDto crear(@Valid @RequestBody VentaRequestDto dto) {
        return ventaService.crear(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VentaResponseDto> listar() {
        return ventaService.listar();
    }

    @GetMapping("/id/{idVenta}")
    @ResponseStatus(HttpStatus.OK)
    public VentaResponseDto buscarPorId(@PathVariable Long idVenta) {
        return ventaService.buscarPorId(idVenta);
    }

    @GetMapping("/cliente/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public List<VentaResponseDto> buscarPorCliente(@PathVariable Long idCliente) {
        return ventaService.buscarPorCliente(idCliente);
    }
}