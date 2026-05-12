package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.VentaRequestDto;
import com.gyl.CrudGyl.dto.response.VentaResponseDto;
import com.gyl.CrudGyl.entity.Cliente;
import com.gyl.CrudGyl.entity.DetalleVenta;
import com.gyl.CrudGyl.entity.Producto;
import com.gyl.CrudGyl.entity.Venta;
import com.gyl.CrudGyl.exception.ProductoInactivoException;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.exception.StockInsuficienteException;
import com.gyl.CrudGyl.exception.VentaSinItemsException;
import com.gyl.CrudGyl.mapper.DetalleVentaMapper;
import com.gyl.CrudGyl.mapper.VentaMapper;
import com.gyl.CrudGyl.repository.ClienteRepository;
import com.gyl.CrudGyl.repository.DetalleVentaRepository;
import com.gyl.CrudGyl.repository.ProductoRepository;
import com.gyl.CrudGyl.repository.VentaRepository;
import com.gyl.CrudGyl.service.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public VentaServiceImpl(
            VentaRepository ventaRepository,
            ClienteRepository clienteRepository,
            ProductoRepository productoRepository,
            DetalleVentaRepository detalleVentaRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional
    public VentaResponseDto crear(VentaRequestDto dto) {
        validarItems(dto);

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro el cliente con ID " + dto.idCliente()
                ));

        Venta venta = VentaMapper.toEntity(cliente);
        Venta guardada = ventaRepository.save(venta);
        List<DetalleVenta> detalles = crearDetalles(dto, guardada);

        guardada.setTotalVenta(calcularTotal(detalles));
        Venta ventaActualizada = ventaRepository.save(guardada);

        return construirResponseConDetalles(ventaActualizada);
    }

    @Override
    public List<VentaResponseDto> listar() {
        return ventaRepository.findAll()
                .stream()
                .map(this::construirResponseConDetalles)
                .toList();
    }

    @Override
    public VentaResponseDto buscarPorId(Long idVenta) {
        return ventaRepository.findById(idVenta)
                .map(this::construirResponseConDetalles)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontro la venta con ID " + idVenta
                ));
    }



    @Override
    public List<VentaResponseDto> buscarPorCliente(Long idCliente) {
        return ventaRepository.findByClienteIdCliente(idCliente)
                .stream()
                .map(this::construirResponseConDetalles)
                .toList();
    }

    private List<DetalleVenta> crearDetalles(VentaRequestDto dto, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();

        dto.items().forEach(item -> {
            Producto producto = productoRepository.findById(item.idProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontro el producto con ID " + item.idProducto()
                    ));

            validarProductoActivo(producto);

            if (producto.getStock() < item.cantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto " + producto.getNombre()
                );
            }

            producto.setStock(producto.getStock() - item.cantidad());

            DetalleVenta detalle = DetalleVentaMapper.toEntity(venta, producto, item.cantidad());
            detalles.add(detalleVentaRepository.save(detalle));
        });

        return detalles;
    }

    private void validarItems(VentaRequestDto dto) {
        if (dto.items() == null || dto.items().isEmpty()) {
            throw new VentaSinItemsException("La venta debe tener al menos un producto");
        }
    }

    private void validarProductoActivo(Producto producto) {
        if (!producto.getActivo()) {
            throw new ProductoInactivoException(
                    "El producto " + producto.getNombre() + " esta inactivo"
            );
        }
    }

    private BigDecimal calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private VentaResponseDto construirResponseConDetalles(Venta venta) {
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaIdVenta(venta.getIdVenta());
        return VentaMapper.toResponseDto(
                venta,
                detalles.stream()
                        .map(DetalleVentaMapper::toResponseDto)
                        .toList()
        );
    }
}
