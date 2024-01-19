package com.adibahsyariah.api.services;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.adibahsyariah.api.entity.User;
import com.adibahsyariah.api.models.createUserRequest;
import com.adibahsyariah.api.models.UserResponse;
import com.adibahsyariah.api.models.updateUser;
import com.adibahsyariah.api.repository.userRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Configuration
@Service
public class userService {
  @Autowired
  private userRepository userRepo;
  
  @Autowired
  private Validator validator;
  
    @Transactional
    public Object deleteUser(Long id){
      if(!userRepo.existsById(id)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data Not Found");
       }
      userRepo.deleteById(id);
      return "Success";
    }

    @Transactional
    public UserResponse register(createUserRequest request){
       Set<ConstraintViolation<createUserRequest>> constraintViolations = validator.validate(request);
       if(constraintViolations.size() != 0){
         throw new ConstraintViolationException(constraintViolations);
       }

       if(userRepo.existsByEmail(request.getEmail())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists");
       }
       User user = new User();
       user.setEmail(request.getEmail());
       user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
       user.setBirthDay(request.getBirthDay());
       user.setName(request.getName());
       user.setPhone(request.getPhone());
       user.setRole_id(request.getRole_id());
       userRepo.save(user);
      return UserResponse.builder()
      .user(user).build();
    }

    @Transactional
    public List<User> GetAllUser(){
      List<User> users = userRepo.findAll();
      return users;
    }

    @Transactional
    public Optional<User> GetUserById(Long id){
      if(!userRepo.existsById(id)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data Not Found");
       }
      Optional<User> users = userRepo.findById(id);
      return users;
    }

    @Transactional
    public Optional<User> updateUser(Long id,updateUser request){
      if(!userRepo.existsById(id)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data Not Found");
       }
      Optional<User> users = userRepo.findById(id);
        User user = users.get();

        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setBirthDay(request.getBirthDay());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole_id(request.getRole_id());

        // Simpan kembali objek User yang diperbarui ke database
        userRepo.save(user);
        return users;
    }


    @Bean
    public UserDetailsService userDetailsService(){
      return email -> userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public AuthenticationProvider authProvider(){
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
      return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }
}
