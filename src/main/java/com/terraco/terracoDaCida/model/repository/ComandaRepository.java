package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    @Query("Select c from Comanda c where c.dataExclusao is null order by c.situacaoComanda asc")
    List<Comanda> findAllWhereDataExclusaoIsNull();
    @Query("Select c from Comanda c join c.cliente cl where cl.id = :idCliente and c.dataExclusao is null order by c.dataCriacao desc")
    List<Comanda> findByIdClienteAndDataExclusaoIsNull(Long idCliente);
    @Query("Select c from Comanda c where c.id = :idComanda and c.dataExclusao is null")
    Optional<Comanda> findByIdAndDataExclusaoIsNull(Long idComanda);
}
