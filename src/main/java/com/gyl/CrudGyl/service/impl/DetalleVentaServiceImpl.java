package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.DetalleVentaRequestDto;
import com.gyl.CrudGyl.dto.response.DetalleVentaResponseDto;
import com.gyl.CrudGyl.entity.DetalleVenta;
import com.gyl.CrudGyl.entity.Producto;
import com.gyl.CrudGyl.entity.Venta;
import com.gyl.CrudGyl.exception.ProductoInactivoException;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.exception.StockInsuficienteException;
import com.gyl.CrudGyl.mapper.DetalleVentaMapper;
import com.gyl.CrudGyl.repository.DetalleVentaRepository;
import com.gyl.CrudGyl.repository.ProductoRepository;
import com.gyl.CrudGyl.repository.VentaRepository;
import com.gyl.CrudGyl.service.DetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Transactional
    public DetalleVentaResponseDto crear(DetalleVentaRequestDto dto) {
        Venta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro la venta con ID " + dto.idVenta()
                ));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el producto con ID " + dto.idProducto()
                ));

        validarProductoActivo(producto);
        validarStock(producto, dto.cantidad());
        producto.setStock(producto.getStock() - dto.cantidad());

        DetalleVenta detalle = DetalleVentaMapper.toEntity(dto, venta, producto);
        DetalleVenta guardado = detalleVentaRepository.save(detalle);
        recalcularTotalVenta(venta.getIdVenta());

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
                        "No se encontro el detalle de venta con ID " + idDetalleVenta
                ));
    }

    @Override
    @Transactional
    public DetalleVentaResponseDto actualizar(Long idDetalleVenta, DetalleVentaRequestDto dto) {
        DetalleVenta detalle = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el detalle de venta con ID " + idDetalleVenta
                ));

        Venta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro la venta con ID " + dto.idVenta()
                ));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el producto con ID " + dto.idProducto()
                ));

        validarProductoActivo(producto);

        Long idVentaAnterior = detalle.getVenta().getIdVenta();
        Producto productoAnterior = detalle.getProducto();
        productoAnterior.setStock(productoAnterior.getStock() + detalle.getCantidad());

        validarStock(producto, dto.cantidad());
        producto.setStock(producto.getStock() - dto.cantidad());

        DetalleVentaMapper.updateEntity(detalle, dto, venta, producto);
        DetalleVenta guardado = detalleVentaRepository.save(detalle);
        recalcularTotalVenta(idVentaAnterior);
        recalcularTotalVenta(venta.getIdVenta());

        return DetalleVentaMapper.toResponseDto(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Long idDetalleVenta) {
        DetalleVenta detalle = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el detalle de venta con ID " + idDetalleVenta
                ));

        Long idVenta = detalle.getVenta().getIdVenta();
        Producto producto = detalle.getProducto();
        producto.setStock(producto.getStock() + detalle.getCantidad());
        detalleVentaRepository.delete(detalle);
        recalcularTotalVenta(idVenta);
    }

    @Override
    public List<DetalleVentaResponseDto> buscarPorVenta(Long idVenta) {
        return detalleVentaRepository.findByVentaIdVenta(idVenta)
                .stream()
                .map(DetalleVentaMapper::toResponseDto)
                .toList();
    }

    private void validarStock(Producto producto, Integer cantidad) {
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para el producto " + producto.getNombre()
            );
        }
    }

    private void validarProductoActivo(Producto producto) {
        if (!producto.getActivo()) {
            throw new ProductoInactivoException(
                    "El producto " + producto.getNombre() + " esta inactivo"
            );
        }
    }

    private void recalcularTotalVenta(Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro la venta con ID " + idVenta
                ));

        BigDecimal total = detalleVentaRepository.findByVentaIdVenta(idVenta)
                .stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotalVenta(total);
        ventaRepository.save(venta);
    }
}
