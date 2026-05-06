package com.gyl.CrudGyl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "venta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idVenta;

    @Column(nullable = false)
    private LocalDate fechaVenta;

    @Column(nullable = false)
    private BigDecimal totalVenta;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
}
