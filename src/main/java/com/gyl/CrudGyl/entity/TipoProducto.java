package com.gyl.CrudGyl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTipoProducto;

    @Column(nullable = false, length = 100)
    private String nombreTipoProducto;
}