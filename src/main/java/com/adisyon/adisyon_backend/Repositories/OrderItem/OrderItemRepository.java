package com.adisyon.adisyon_backend.Repositories.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
