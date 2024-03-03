package com.asdc.funderbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asdc.funderbackend.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{

}
