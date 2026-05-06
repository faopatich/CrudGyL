package com.gyl.CrudGyl.repository;

import com.gyl.CrudGyl.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombreCliente (String nombreCliente);
    List<Cliente> findByActivoTrue();

    List<Cliente> findByNombreClienteAndActivoTrue(String nombreCliente);
}
