package com.adisyon.adisyon_backend.Repositories.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adisyon.adisyon_backend.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCompanyId(Long companyId);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% OR p.productCategory.productCategoryName LIKE %:keyword%")
    List<Product> searchProduct(@Param("keyword") String keyword);
}
