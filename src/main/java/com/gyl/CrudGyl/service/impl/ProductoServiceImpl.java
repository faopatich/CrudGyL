package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.ProductRequestDto;
import com.gyl.CrudGyl.dto.response.ProductResponseDto;
import com.gyl.CrudGyl.entity.Producto;
import com.gyl.CrudGyl.entity.TipoProducto;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.mapper.ProductoMapper;
import com.gyl.CrudGyl.repository.ProductoRepository;
import com.gyl.CrudGyl.repository.TipoProductoRepository;
import com.gyl.CrudGyl.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            TipoProductoRepository tipoProductoRepository
    ) {
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }
    @Override
    public List <ProductResponseDto> busquedaPorNombre(String nombre){
        return productoRepository.findByNombreAndActivoTrue(nombre)
                .stream()
                .map(ProductoMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto crear(ProductRequestDto dto) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(dto.idTipoProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el tipo de producto con ID " + dto.idTipoProducto()
                ));

        Producto producto = ProductoMapper.toEntity(dto);
        producto.setTipoProducto(tipoProducto);

        Producto guardado = productoRepository.save(producto);

        return ProductoMapper.toResponseDto(guardado);
    }

    @Override
    public List<ProductResponseDto> listar() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .filter(Producto::getActivo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el ID " + id
                ));

        return ProductoMapper.toResponseDto(producto);
    }

    @Override
    public ProductResponseDto actualizar(Long id, ProductRequestDto dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException(
                        "No se encontro el ID" + id

                ));
        ProductoMapper.updateEntity(producto, dto);
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toResponseDto(guardado);

    }

    @Override
    public void eliminar(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el id " + id
                ));

        producto.setActivo(false);
        productoRepository.save(producto);
    }
}

