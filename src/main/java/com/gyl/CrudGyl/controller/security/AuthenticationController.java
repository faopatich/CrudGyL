package com.gyl.CrudGyl.controller.security;

import com.gyl.CrudGyl.dto.request.LoginRequestDTO;
import com.gyl.CrudGyl.dto.request.RegistroRequestDTO;
import com.gyl.CrudGyl.dto.response.RegistroResponseDTO;
import com.gyl.CrudGyl.dto.response.TokenResponseDTO;
import com.gyl.CrudGyl.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponseDTO registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        return authenticationService.registrar(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDTO autenticar(@Valid @RequestBody LoginRequestDTO dto) {
        return authenticationService.login(dto);
    }
}