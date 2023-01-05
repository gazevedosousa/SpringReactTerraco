package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.TipoLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoLoginRepository extends JpaRepository<TipoLogin, Long> {

    Optional<TipoLogin> findByCoTipoLoginAndDhExclusaoIsNull(Long coTipoLogin);
}
