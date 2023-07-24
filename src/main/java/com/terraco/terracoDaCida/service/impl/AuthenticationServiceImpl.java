package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.JwtAuthDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.exceptions.NaoAutorizadoException;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.enums.PerfilEnum;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import com.terraco.terracoDaCida.service.AuthenticationService;
import com.terraco.terracoDaCida.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final LoginRepository userRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public LoginDTOView signup(LoginDTO request) {
        try {
            var user = Login.builder().noUsuario(request.getNoUsuario())
                    .coSenha(passwordEncoder.encode(request.getCoSenha()))
                    .perfil(PerfilEnum.valueOf(request.getPerfil()))
                    .dataCriacao(LocalDateTime.now())
                    .dataAtualizacao(LocalDateTime.now()).build();
            userRepository.save(user);
            return loginMapper.toDto(user);
        } catch (Exception e) {
            throw new NaoAutorizadoException("Erro ao cadastrar usuário no banco de dados");
        }
    }

    @Override
    public JwtAuthDTO signin(LoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getNoUsuario(), request.getCoSenha()));
        } catch (Exception e) {
            throw new NaoAutorizadoException("Usuário ou Senha inválidos");
        }
        var user = userRepository.findByNoUsuarioAndDataExclusaoIsNull(request.getNoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou Senha inválidos"));
        var jwt = jwtService.generateToken(user);
        var login = loginMapper.toDto(user);
        return JwtAuthDTO.builder().token(jwt).login(login).build();
    }
}

