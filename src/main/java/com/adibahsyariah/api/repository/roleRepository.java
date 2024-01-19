package com.adibahsyariah.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adibahsyariah.api.entity.Role;

@Repository
public interface roleRepository extends JpaRepository<Role, Long>{
    
}

