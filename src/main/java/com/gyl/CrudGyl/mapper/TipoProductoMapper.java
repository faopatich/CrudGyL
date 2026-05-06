package com.gyl.CrudGyl.mapper;

import com.gyl.CrudGyl.dto.request.TipoProductoRequestDto;
import com.gyl.CrudGyl.dto.response.TipoProductoResponseDto;
import com.gyl.CrudGyl.entity.TipoProducto;

public class TipoProductoMapper {

    private TipoProductoMapper() {}

    public static TipoProducto toEntity(TipoProductoRequestDto dto) {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setNombreTipoProducto(dto.nombreTipoProducto());
        return tipoProducto;
    }

    public static TipoProductoResponseDto toResponseDto(TipoProducto tipoProducto) {
        return new TipoProductoResponseDto(
                tipoProducto.getIdTipoProducto(),
                tipoProducto.getNombreTipoProducto()
        );
    }

    public static void updateEntity(TipoProducto tipoProducto, TipoProductoRequestDto dto) {
        tipoProducto.setNombreTipoProducto(dto.nombreTipoProducto());
    }
}