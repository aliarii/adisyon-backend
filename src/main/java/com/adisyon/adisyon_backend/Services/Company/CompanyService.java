package com.adisyon.adisyon_backend.Services.Company;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyService {
    public List<Company> getAllCompanies();

    public Company getCompanyById(Long id);

    public Company createCompany(CreateCompanyDto companyDto, Long ownerId);

    // public Company updateCompany(UpdateCompanyDto companyDto);

    // public void deleteCompany(DeleteCompanyDto companyDto);
}
