package com.adibahsyariah.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adibahsyariah.api.models.createUserRequest;
import com.adibahsyariah.api.models.UserResponse;
import com.adibahsyariah.api.models.WebResponse;
import com.adibahsyariah.api.models.updateUser;
import com.adibahsyariah.api.services.authService;
import com.adibahsyariah.api.services.userService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@CrossOrigin
@RequestMapping("/api/user/")
public class userController {

    @Autowired
    private userService userService;
    @Autowired
    private authService authService;

    @GetMapping
    public WebResponse<Object> getAllData() {
        return WebResponse.<Object>builder().data(userService.GetAllUser()).build();
    }
    
    @GetMapping("{id}")
    public WebResponse<Object> getDataById(@PathVariable Long id) {
        return WebResponse.<Object>builder().data(userService.GetUserById(id)).build();
    }

    @GetMapping("me")
    public WebResponse<UserResponse> getUserlogin() {
            return WebResponse.<UserResponse>builder().data(authService.getUser()).build();
    }
    
     @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> Register(@RequestBody createUserRequest request) {

        return WebResponse.<UserResponse>builder().data(userService.register(request)).build();
    }

     @PutMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Object> Update(@RequestParam Long id ,@RequestBody updateUser request) {

        return WebResponse.<Object>builder().data(userService.updateUser(id,request)).build();
    }

    @DeleteMapping("{id}")
      public WebResponse<Object> deleteUser(@PathVariable Long id) {
        return WebResponse.<Object>builder().data(userService.deleteUser(id)).build();
    }
}
