package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
