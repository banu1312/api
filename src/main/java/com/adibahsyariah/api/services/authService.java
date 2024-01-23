package com.adibahsyariah.api.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.adibahsyariah.api.entity.User;
import com.adibahsyariah.api.models.LoginRequest;
import com.adibahsyariah.api.models.LoginResponse;
import com.adibahsyariah.api.models.UserResponse;
import com.adibahsyariah.api.repository.userRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class authService {

    private final userRepository userRepository;
    private final jwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse auth(LoginRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
    
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
    
            return LoginResponse.builder().token(jwtToken).build();
        }
        catch(AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Failed", e);
        }
    }
    public UserResponse getUser() {
        // Mengambil informasi user yang sedang login dari konteks keamanan
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Jika user sudah login, kita dapat mengakses informasi user
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return UserResponse.builder().user(user).build();
        } else {
            // Jika user belum login atau tidak ada informasi user, Anda dapat menangani secara sesuai
           throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You cannot access this Data");
        }
    }
}
