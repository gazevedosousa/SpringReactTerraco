package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    boolean existsByNoUsuario(String usuario);
}
