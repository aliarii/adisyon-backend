package com.adisyon.adisyon_backend.Repositories.BasketCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.BasketCategory;

public interface BasketCategoryRepository extends JpaRepository<BasketCategory, Long> {
    public List<BasketCategory> findByCompanyId(Long id);

}
