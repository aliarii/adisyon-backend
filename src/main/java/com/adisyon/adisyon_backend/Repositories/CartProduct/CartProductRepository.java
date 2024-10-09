package com.adisyon.adisyon_backend.Repositories.CartProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    public CartProduct findByCartId(Long id);

}
