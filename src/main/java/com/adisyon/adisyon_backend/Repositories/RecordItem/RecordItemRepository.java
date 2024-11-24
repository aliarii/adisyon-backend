package com.adisyon.adisyon_backend.Repositories.RecordItem;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adisyon.adisyon_backend.Entities.RecordItem;

public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {
    public List<RecordItem> findByCompanyId(Long companyId);

    @Query("SELECT r FROM RecordItem r WHERE r.company.id = :companyId AND r.createdDate BETWEEN :startDate AND :endDate")
    public List<RecordItem> findRecordItemsByMonth(@Param("companyId") Long companyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
