package com.adisyon.adisyon_backend.Repositories.Basket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    public List<Basket> findByCompanyId(Long id);
}
