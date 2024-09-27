package com.adisyon.adisyon_backend.Repositories.BasketProduct;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adisyon.adisyon_backend.Entities.BasketProduct;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
    @Query("SELECT bp FROM BasketProduct bp JOIN bp.basket b WHERE b.id = :id")
    List<BasketProduct> findByBasketId(Long id);
}
