package com.adibahsyariah.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adibahsyariah.api.entity.User;
import com.adibahsyariah.api.models.LoginRequest;
import com.adibahsyariah.api.models.LoginResponse;
import com.adibahsyariah.api.models.UserResponse;
import com.adibahsyariah.api.models.WebResponse;
import com.adibahsyariah.api.models.createUserRequest;
import com.adibahsyariah.api.services.authService;
import com.adibahsyariah.api.services.paymentService;
import com.adibahsyariah.api.services.userService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
@RequiredArgsConstructor
public class authController {
     private final authService authService;
     private final userService userService;
     private final paymentService paymentService;
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
     @GetMapping("{path}")
    public ResponseEntity<?> getImage(@PathVariable String path) throws IOException {
        byte [] imageData = paymentService.getImage(path);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}