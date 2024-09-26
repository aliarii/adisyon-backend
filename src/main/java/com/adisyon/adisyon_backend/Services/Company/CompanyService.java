package com.adisyon.adisyon_backend.Services.Company;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyService {
    public List<Company> getAllCompanies();

    public Company getCompanyById(Long id);

    public Company createCompany(Company company);

    public Company updateCompany(Long id, Company companyDetails);

    public void deleteCompany(Long id);
}
