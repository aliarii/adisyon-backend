package com.adisyon.adisyon_backend.Repositories.Company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByIsActive(Boolean isActive);
}
