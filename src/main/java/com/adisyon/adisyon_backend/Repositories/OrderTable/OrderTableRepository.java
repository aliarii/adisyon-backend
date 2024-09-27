package com.adisyon.adisyon_backend.Repositories.OrderTable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.OrderTable;

public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
    public List<OrderTable> findByCompanyId(Long id);
}
