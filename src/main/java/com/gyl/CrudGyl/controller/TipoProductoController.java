package com.gyl.CrudGyl.controller;

import com.gyl.CrudGyl.dto.request.TipoProductoRequestDto;
import com.gyl.CrudGyl.dto.response.TipoProductoResponseDto;
import com.gyl.CrudGyl.service.TipoProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoProductoResponseDto crear(@Valid @RequestBody TipoProductoRequestDto dto) {
        return tipoProductoService.crear(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TipoProductoResponseDto> listar() {
        return tipoProductoService.listar();
    }

    @GetMapping("/id/{idTipoProducto}")
    @ResponseStatus(HttpStatus.OK)
    public TipoProductoResponseDto buscarPorId(@PathVariable Long idTipoProducto) {
        return tipoProductoService.buscarPorId(idTipoProducto);
    }

    @PutMapping("/{idTipoProducto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TipoProductoResponseDto actualizar(
            @PathVariable Long idTipoProducto,
            @Valid @RequestBody TipoProductoRequestDto dto
    ) {
        return tipoProductoService.actualizar(idTipoProducto, dto);
    }

    @DeleteMapping("/id/{idTipoProducto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void eliminar(@PathVariable Long idTipoProducto) {
        tipoProductoService.eliminar(idTipoProducto);
    }

    @GetMapping("/nombre/{nombreTipoProducto}")
    @ResponseStatus(HttpStatus.OK)
    public List<TipoProductoResponseDto> busquedaPorNombre(@PathVariable String nombreTipoProducto) {
        return tipoProductoService.busquedaPorNombre(nombreTipoProducto);
    }
}