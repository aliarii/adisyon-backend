package com.adisyon.adisyon_backend.Repositories.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByBasketId(Long id);

    public List<Order> findByCompanyId(Long id);
}
