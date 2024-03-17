package com.adibahsyariah.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adibahsyariah.api.entity.User;
import java.util.Optional;



@Repository
public interface userRepository extends JpaRepository<User, Long>{

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);    
}
