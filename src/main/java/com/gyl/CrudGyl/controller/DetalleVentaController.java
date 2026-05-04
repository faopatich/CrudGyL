package com.gyl.CrudGyl.controller;

import com.gyl.CrudGyl.dto.DetalleVentaRequestDto;
import com.gyl.CrudGyl.dto.DetalleVentaResponseDto;
import com.gyl.CrudGyl.service.DetalleVentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleVentaResponseDto crear(@Valid @RequestBody DetalleVentaRequestDto dto) {
        return detalleVentaService.crear(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DetalleVentaResponseDto> listar() {
        return detalleVentaService.listar();
    }

    @GetMapping("/id/{idDetalleVenta}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleVentaResponseDto buscarPorId(@PathVariable Long idDetalleVenta) {
        return detalleVentaService.buscarPorId(idDetalleVenta);
    }

    @PutMapping("/{idDetalleVenta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DetalleVentaResponseDto actualizar(
            @PathVariable Long idDetalleVenta,
            @Valid @RequestBody DetalleVentaRequestDto dto
    ) {
        return detalleVentaService.actualizar(idDetalleVenta, dto);
    }

    @DeleteMapping("/id/{idDetalleVenta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void eliminar(@PathVariable Long idDetalleVenta) {
        detalleVentaService.eliminar(idDetalleVenta);
    }

    @GetMapping("/venta/{idVenta}")
    @ResponseStatus(HttpStatus.OK)
    public List<DetalleVentaResponseDto> buscarPorVenta(@PathVariable Long idVenta) {
        return detalleVentaService.buscarPorVenta(idVenta);
    }
}