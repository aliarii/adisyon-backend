package com.adisyon.adisyon_backend.Services.Company;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Repositories.Company.CompanyRepository;
import com.adisyon.adisyon_backend.Repositories.Owner.OwnerRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    // @Autowired
    // private OwnerService ownerService;

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findCompanyById(Long id) {
        return Unwrapper.unwrap(companyRepository.findById(id), id);
    }

    public Company createCompany(CreateCompanyDto companyDto, Long ownerId) {
        Company newCompany = new Company();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();
        newCompany.setName(companyDto.getName());
        newCompany.setOwner(owner);
        newCompany.setIsActive(true);
        newCompany.setCreatedDate(new Date());
        return companyRepository.save(newCompany);
    }

    // public Company updateCompany(UpdateCompanyDto companyDto) {
    // Company company = unwrapCompany(companyRepository.findById(id), id);
    // // Company company = companyRepository.findById(id)
    // // .orElseThrow(/* () -> new ResourceNotFoundException("Company not found
    // with
    // // id " + id) */);

    // company.setCompanyName(companyDetails.getCompanyName());
    // company.setIsActive(companyDetails.getIsActive());
    // company.setCreatedDate(companyDetails.getCreatedDate());
    // company.setUpdatedDate(companyDetails.getUpdatedDate());

    // return companyRepository.save(company);
    // }

    // public void deleteCompany(DeleteCompanyDto companyDto) {
    // Company company = unwrapCompany(companyRepository.findById(id), id);
    // // Company company=companyRepository.findById(id);
    // // .orElseThrow(/* () -> new ResourceNotFoundException("Company not found
    // with
    // // id " + id) */);
    // companyRepository.delete(company);
    // }

}
