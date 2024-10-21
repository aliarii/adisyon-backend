package com.adisyon.adisyon_backend.Repositories.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
