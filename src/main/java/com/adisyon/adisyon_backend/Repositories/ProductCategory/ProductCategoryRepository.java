package com.adisyon.adisyon_backend.Repositories.ProductCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    public List<ProductCategory> findByCompanyId(Long id);

}
