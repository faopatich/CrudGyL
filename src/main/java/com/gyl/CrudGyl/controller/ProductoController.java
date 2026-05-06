package com.gyl.CrudGyl.controller;

import com.gyl.CrudGyl.dto.request.ProductRequestDto;
import com.gyl.CrudGyl.dto.response.ProductResponseDto;
import com.gyl.CrudGyl.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto crear(@Valid @RequestBody ProductRequestDto dto){
        return productoService.crear(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> listar() {
        return productoService.listar();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponseDto actualizar (@PathVariable Long id, @Valid @RequestBody ProductRequestDto dto) {
        return productoService.actualizar(id, dto);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }

    @GetMapping("/nombre/{nombre}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> busquedaPorNombre(@Valid @PathVariable String nombre) {
        return productoService.busquedaPorNombre(nombre);
    }
}
