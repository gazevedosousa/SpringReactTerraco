package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheLoginServiceImpl implements UserDetailsService {

    private final LoginRepository repository;

    public DetalheLoginServiceImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> login = repository.findByNoUsuarioAndDataExclusaoIsNull(username);

        return login.orElseThrow(() -> new UsernameNotFoundException(
                "Usuário [" + username + "] não encontrado!"));
    }
}
