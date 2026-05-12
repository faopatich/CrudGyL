package com.gyl.CrudGyl.service;

import com.gyl.CrudGyl.dto.request.LoginRequestDTO;
import com.gyl.CrudGyl.dto.request.RegistroRequestDTO;
import com.gyl.CrudGyl.dto.response.RegistroResponseDTO;
import com.gyl.CrudGyl.dto.response.TokenResponseDTO;

public interface AuthenticationService {
    RegistroResponseDTO registrar(RegistroRequestDTO dto);

    TokenResponseDTO login(LoginRequestDTO dto);
}