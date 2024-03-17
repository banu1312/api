package com.adibahsyariah.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adibahsyariah.api.entity.Payment;

@Repository
public interface paymentRepository extends JpaRepository<Payment, Long>{
    
}