package com.gyl.CrudGyl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 100)
    private String nombre;

    @Column(nullable=false)
    private Double precio;

    @Column(nullable=false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "idTipoProducto", nullable = false)
    private TipoProducto tipoProducto;

    @Column(nullable = false)
    private Boolean activo = true;
}

