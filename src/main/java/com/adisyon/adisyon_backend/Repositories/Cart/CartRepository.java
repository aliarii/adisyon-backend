package com.adisyon.adisyon_backend.Repositories.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findByBasketId(Long id);

}
