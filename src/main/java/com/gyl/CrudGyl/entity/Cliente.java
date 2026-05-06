package com.gyl.CrudGyl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCliente;

    @Column(nullable=false, length = 100)
    private String nombreCliente;

    @Column(nullable = false)
    private String apellidoCliente;

    @Column(nullable = false)
    private String correoCliente;

    @Column(nullable = false)
    private String telefonoCliente;

    @Column(nullable = false)
    private String direccionCliente;

    @Column(nullable = false)
    private Boolean activo = true;
}
