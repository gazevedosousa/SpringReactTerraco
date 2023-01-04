package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
}
