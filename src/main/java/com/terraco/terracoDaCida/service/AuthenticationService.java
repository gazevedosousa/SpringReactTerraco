package com.terraco.terracoDaCida.service;


import com.terraco.terracoDaCida.api.dto.JwtAuthDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;

public interface AuthenticationService {
    LoginDTOView signup(LoginDTO request);

    JwtAuthDTO signin(LoginDTO request);
}