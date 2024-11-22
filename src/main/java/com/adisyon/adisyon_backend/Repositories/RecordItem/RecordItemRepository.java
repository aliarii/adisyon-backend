package com.adisyon.adisyon_backend.Repositories.RecordItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.RecordItem;

public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {
    public List<RecordItem> findByCompanyId(Long companyId);

}
