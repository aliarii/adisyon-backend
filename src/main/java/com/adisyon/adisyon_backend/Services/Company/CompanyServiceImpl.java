package com.adisyon.adisyon_backend.Services.Company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Exception.NotFoundException;
import com.adisyon.adisyon_backend.Repositories.Company.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company company = unwrapCompany(companyRepository.findById(id), id);
        // Company company = companyRepository.findById(id)
        // .orElseThrow(/* () -> new ResourceNotFoundException("Company not found with
        // id " + id) */);

        company.setCompanyName(companyDetails.getCompanyName());
        company.setIsActive(companyDetails.getIsActive());
        company.setCreatedDate(companyDetails.getCreatedDate());
        company.setUpdatedDate(companyDetails.getUpdatedDate());

        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        Company company = unwrapCompany(companyRepository.findById(id), id);
        // Company company=companyRepository.findById(id);
        // .orElseThrow(/* () -> new ResourceNotFoundException("Company not found with
        // id " + id) */);
        companyRepository.delete(company);
    }

    static Company unwrapCompany(Optional<Company> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException(id.toString());
    }
}
