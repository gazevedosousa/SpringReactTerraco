package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    @Query("Select c from Comanda c where c.dataExclusao is null")
    List<Comanda> findAllWhereDataExclusaoIsNull();
    Optional<Comanda> findByIdAndDataExclusaoIsNull(Long idComanda);
}
