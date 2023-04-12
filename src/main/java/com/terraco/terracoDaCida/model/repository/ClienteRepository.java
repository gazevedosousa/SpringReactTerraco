package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("Select c from Cliente c where c.dataExclusao is null order by c.noCliente asc")
    List<Cliente> findAllWhereDataExclusaoIsNull();
    boolean existsByNoClienteAndDataExclusaoIsNull(String noCliente);
    Optional<Cliente> findByIdAndDataExclusaoIsNull(Long idCliente);
    @Query("Select c from Cliente c where c.noCliente = :noCliente and c.dataExclusao is null")
    Optional<Cliente> findByNameAndDataExclusaoIsNull(String noCliente);
}
