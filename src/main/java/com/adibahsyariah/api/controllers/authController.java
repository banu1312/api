package com.adibahsyariah.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adibahsyariah.api.models.LoginRequest;
import com.adibahsyariah.api.models.LoginResponse;
import com.adibahsyariah.api.models.UserResponse;
import com.adibahsyariah.api.models.WebResponse;
import com.adibahsyariah.api.models.createUserRequest;
import com.adibahsyariah.api.services.authService;
import com.adibahsyariah.api.services.userService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class authController {
    
     private final authService authService;
     private final userService userService;
    @PostMapping("login")
    public WebResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return WebResponse.<LoginResponse>builder().data(authService.auth(request)).build();
    }
      @PostMapping(
        value = "register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> Register(@RequestBody createUserRequest request) {

        return WebResponse.<UserResponse>builder().data(userService.register(request)).build();
    }
}