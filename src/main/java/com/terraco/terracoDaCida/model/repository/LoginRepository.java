package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    @Query("Select l from Login l where l.dataExclusao is null")
    List<Login> findAllWhereDataExclusaoIsNull();
    boolean existsByNoUsuarioAndDataExclusaoIsNull(String NoUsuario);
    Optional<Login> findByNoUsuarioAndDataExclusaoIsNull(String NoUsuario);
    Optional<Login> findByIdAndDataExclusaoIsNull(Long id);
}
