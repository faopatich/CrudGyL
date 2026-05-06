package com.gyl.CrudGyl.service.impl;

import com.gyl.CrudGyl.dto.request.TipoProductoRequestDto;
import com.gyl.CrudGyl.dto.response.TipoProductoResponseDto;
import com.gyl.CrudGyl.entity.TipoProducto;
import com.gyl.CrudGyl.exception.RecursoNoEncontradoException;
import com.gyl.CrudGyl.mapper.TipoProductoMapper;
import com.gyl.CrudGyl.repository.TipoProductoRepository;
import com.gyl.CrudGyl.service.TipoProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @Override
    public TipoProductoResponseDto crear(TipoProductoRequestDto dto) {
        TipoProducto tipoProducto = TipoProductoMapper.toEntity(dto);
        TipoProducto guardado = tipoProductoRepository.save(tipoProducto);
        return TipoProductoMapper.toResponseDto(guardado);
    }

    @Override
    public List<TipoProductoResponseDto> listar() {
        return tipoProductoRepository.findAll()
                .stream()
                .map(TipoProductoMapper::toResponseDto)
                .toList();
    }

    @Override
    public TipoProductoResponseDto buscarPorId(Long idTipoProducto) {
        return tipoProductoRepository.findById(idTipoProducto)
                .map(TipoProductoMapper::toResponseDto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el tipo de producto con ID " + idTipoProducto
                ));
    }

    @Override
    public TipoProductoResponseDto actualizar(Long idTipoProducto, TipoProductoRequestDto dto) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(idTipoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el tipo de producto con ID " + idTipoProducto
                ));

        TipoProductoMapper.updateEntity(tipoProducto, dto);
        TipoProducto guardado = tipoProductoRepository.save(tipoProducto);
        return TipoProductoMapper.toResponseDto(guardado);
    }

    @Override
    public void eliminar(Long idTipoProducto) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(idTipoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el tipo de producto con ID " + idTipoProducto
                ));

        tipoProductoRepository.delete(tipoProducto);
    }

    @Override
    public List<TipoProductoResponseDto> busquedaPorNombre(String nombreTipoProducto) {
        return tipoProductoRepository.findByNombreTipoProducto(nombreTipoProducto)
                .stream()
                .map(TipoProductoMapper::toResponseDto)
                .toList();
    }
}