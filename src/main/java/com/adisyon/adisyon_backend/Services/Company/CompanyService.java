package com.adisyon.adisyon_backend.Services.Company;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.UpdateCompanyDto;
import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyService {
    public List<Company> findAllCompanies();

    public Company findCompanyById(Long id);

    public Company findCompanyByUserId(Long id);

    public Company findCompanyByEmployeeId(Long id);

    public Company createCompany(CreateCompanyDto companyDto, Long ownerId);

    public Company updateCompany(UpdateCompanyDto companyDto);

    // public void deleteCompany(DeleteCompanyDto companyDto);

}
