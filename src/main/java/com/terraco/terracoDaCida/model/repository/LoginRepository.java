package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoginRepository extends JpaRepository<Login, Long> {
    @Query("Select l from Login l where l.dataExclusao is null order by l.noUsuario asc")
    List<Login> findAllWhereDataExclusaoIsNull();
    boolean existsByNoUsuarioAndDataExclusaoIsNull(String NoUsuario);
    Optional<Login> findByNoUsuarioAndDataExclusaoIsNull(String NoUsuario);
    Optional<Login> findByIdAndDataExclusaoIsNull(Long id);
}
