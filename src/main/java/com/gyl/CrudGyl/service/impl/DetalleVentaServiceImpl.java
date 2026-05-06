package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.DetalleVentaRequestDto;
import com.gyl.CrudGyl.dto.response.DetalleVentaResponseDto;
import com.gyl.CrudGyl.entity.DetalleVenta;
import com.gyl.CrudGyl.entity.Producto;
import com.gyl.CrudGyl.entity.Venta;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.mapper.DetalleVentaMapper;
import com.gyl.CrudGyl.repository.DetalleVentaRepository;
import com.gyl.CrudGyl.repository.ProductoRepository;
import com.gyl.CrudGyl.repository.VentaRepository;
import com.gyl.CrudGyl.service.DetalleVentaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaServiceImpl(
            DetalleVentaRepository detalleVentaRepository,
            VentaRepository ventaRepository,
            ProductoRepository productoRepository
    ) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public DetalleVentaResponseDto crear(DetalleVentaRequestDto dto) {
        Venta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la venta con ID " + dto.idVenta()
                ));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el producto con ID " + dto.idProducto()
                ));

        DetalleVenta detalle = DetalleVentaMapper.toEntity(dto, venta, producto);
        DetalleVenta guardado = detalleVentaRepository.save(detalle);

        return DetalleVentaMapper.toResponseDto(guardado);
    }

    @Override
    public List<DetalleVentaResponseDto> listar() {
        return detalleVentaRepository.findAll()
                .stream()
                .map(DetalleVentaMapper::toResponseDto)
                .toList();
    }

    @Override
    public DetalleVentaResponseDto buscarPorId(Long idDetalleVenta) {
        return detalleVentaRepository.findById(idDetalleVenta)
                .map(DetalleVentaMapper::toResponseDto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el detalle de venta con ID " + idDetalleVenta
                ));
    }

    @Override
    public DetalleVentaResponseDto actualizar(Long idDetalleVenta, DetalleVentaRequestDto dto) {
        DetalleVenta detalle = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el detalle de venta con ID " + idDetalleVenta
                ));

        Venta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la venta con ID " + dto.idVenta()
                ));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el producto con ID " + dto.idProducto()
                ));

        DetalleVentaMapper.updateEntity(detalle, dto, venta, producto);
        DetalleVenta guardado = detalleVentaRepository.save(detalle);

        return DetalleVentaMapper.toResponseDto(guardado);
    }

    @Override
    public void eliminar(Long idDetalleVenta) {
        DetalleVenta detalle = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el detalle de venta con ID " + idDetalleVenta
                ));

        detalleVentaRepository.delete(detalle);
    }

    @Override
    public List<DetalleVentaResponseDto> buscarPorVenta(Long idVenta) {
        return detalleVentaRepository.findByVentaIdVenta(idVenta)
                .stream()
                .map(DetalleVentaMapper::toResponseDto)
                .toList();
    }
}