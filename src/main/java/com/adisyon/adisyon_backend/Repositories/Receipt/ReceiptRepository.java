package com.adisyon.adisyon_backend.Repositories.Receipt;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adisyon.adisyon_backend.Entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    public List<Receipt> findByCompanyId(Long id);

    @Query("SELECT r FROM Receipt r WHERE r.company.id = :companyId AND r.createdDate BETWEEN :startDate AND :endDate")
    public List<Receipt> getMonthlyReceipts(@Param("companyId") Long companyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
