package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.VentaRequestDto;
import com.gyl.CrudGyl.dto.response.VentaResponseDto;
import com.gyl.CrudGyl.entity.Cliente;
import com.gyl.CrudGyl.entity.Venta;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.mapper.VentaMapper;
import com.gyl.CrudGyl.repository.ClienteRepository;
import com.gyl.CrudGyl.repository.VentaRepository;
import com.gyl.CrudGyl.service.VentaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public VentaServiceImpl(VentaRepository ventaRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public VentaResponseDto crear(VentaRequestDto dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el cliente con ID " + dto.idCliente()
                ));

        Venta venta = new Venta();
        venta.setFechaVenta(dto.fechaVenta());
        venta.setTotalVenta(dto.totalVenta());
        venta.setCliente(cliente);

        Venta guardada = ventaRepository.save(venta);
        return VentaMapper.toResponseDto(guardada);
    }

    @Override
    public List<VentaResponseDto> listar() {
        return ventaRepository.findAll()
                .stream()
                .map(VentaMapper::toResponseDto)
                .toList();
    }

    @Override
    public VentaResponseDto buscarPorId(Long idVenta) {
        return ventaRepository.findById(idVenta)
                .map(VentaMapper::toResponseDto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la venta con ID " + idVenta
                ));
    }

    @Override
    public VentaResponseDto actualizar(Long idVenta, VentaRequestDto dto) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la venta con ID " + idVenta
                ));

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el cliente con ID " + dto.idCliente()
                ));

        venta.setFechaVenta(dto.fechaVenta());
        venta.setTotalVenta(dto.totalVenta());
        venta.setCliente(cliente);

        Venta guardada = ventaRepository.save(venta);
        return VentaMapper.toResponseDto(guardada);
    }

    @Override
    public void eliminar(Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la venta con ID " + idVenta
                ));

        ventaRepository.delete(venta);
    }

    @Override
    public List<VentaResponseDto> buscarPorCliente(Long idCliente) {
        return ventaRepository.findByClienteIdCliente(idCliente)
                .stream()
                .map(VentaMapper::toResponseDto)
                .toList();
    }
}