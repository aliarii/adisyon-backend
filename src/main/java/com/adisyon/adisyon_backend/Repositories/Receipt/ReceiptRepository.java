package com.adisyon.adisyon_backend.Repositories.Receipt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    public List<Receipt> findByCompanyId(Long id);

}
