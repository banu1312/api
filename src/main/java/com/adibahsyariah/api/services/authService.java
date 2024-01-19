package com.adibahsyariah.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.adibahsyariah.api.models.LoginRequest;
import com.adibahsyariah.api.models.LoginResponse;
import com.adibahsyariah.api.repository.userRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class authService {

    private final userRepository userRepository;
    private final jwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse auth(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder().token(jwtToken).build();
    }
}
