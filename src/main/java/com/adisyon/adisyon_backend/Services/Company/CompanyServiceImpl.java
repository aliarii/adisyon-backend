package com.adisyon.adisyon_backend.Services.Company;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.DailyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.MonthlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.UpdateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.YearlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Owner;
import com.adisyon.adisyon_backend.Repositories.Company.CompanyRepository;
import com.adisyon.adisyon_backend.Repositories.Owner.OwnerRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BasketService basketService;

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findCompanyById(Long id) {
        return Unwrapper.unwrap(companyRepository.findById(id), id);
    }

    @Override
    public Company findCompanyByUserId(Long id) {
        return Unwrapper.unwrap(companyRepository.findByOwnerId(id), id);
    }

    @Override
    public Company findCompanyByEmployeeId(Long id) {
        return Unwrapper.unwrap(companyRepository.findByEmployeesId(id), id);
    }

    @Override
    @Transactional
    public Company createCompany(CreateCompanyDto companyDto, Long ownerId) {
        Company newCompany = new Company();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();
        newCompany.setName(companyDto.getName());
        newCompany.setOwner(owner);
        newCompany.setIsActive(true);
        newCompany.setCreatedDate(LocalDateTime.now());

        companyRepository.save(newCompany);

        for (int i = 0; i < 10; i++) {

            CreateBasketDto basketDto = new CreateBasketDto();
            basketDto.setName("Basket " + (i + 1));
            basketDto.setCompany(newCompany);

            Basket createdBasket = basketService.createBasket(basketDto, null);

            newCompany.getBaskets().add(createdBasket);
        }

        return newCompany;
    }

    @Override
    @Transactional
    public Company updateCompany(UpdateCompanyDto companyDto) {
        Company company = findCompanyById(companyDto.getId());
        company.setName(companyDto.getName());
        company.setUpdatedDate(LocalDateTime.now());

        return companyRepository.save(company);
    }

    // public void deleteCompany(DeleteCompanyDto companyDto) {
    // Company company = unwrapCompany(companyRepository.findById(id), id);
    // // Company company=companyRepository.findById(id);
    // // .orElseThrow(/* () -> new ResourceNotFoundException("Company not found
    // with
    // // id " + id) */);
    // companyRepository.delete(company);
    // }

    @Override
    @Transactional
    public DailyReportResponseDto getDailyReport(DailyReportRequestDto requestDto) {

        DailyReportResponseDto responseDto = companyRepository.getDailyReport(requestDto.getCompanyId(),
                requestDto.getDate());
        if (responseDto == null)
            responseDto = new DailyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, null, requestDto.getDate());

        return responseDto;
    }

    @Override
    @Transactional
    public YearlyReportResponseDto getYearlyReport(YearlyReportRequestDto requestDto) {

        YearlyReportResponseDto responseDto = companyRepository.getYearlyReport(requestDto.getCompanyId(),
                requestDto.getYear());
        if (responseDto == null)
            responseDto = new YearlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, requestDto.getYear());

        return responseDto;
    }

    @Override
    @Transactional
    public MonthlyReportResponseDto getMonthlyReport(MonthlyReportRequestDto requestDto) {

        MonthlyReportResponseDto responseDto = companyRepository.getMonthlyReport(requestDto.getCompanyId(),
                requestDto.getYear(), requestDto.getMonth());
        if (responseDto == null)
            responseDto = new MonthlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, requestDto.getMonth(),
                    requestDto.getYear());

        return responseDto;
    }

    @Override
    @Transactional
    public List<DailyReportResponseDto> getDailyReportsForMonth(MonthlyReportRequestDto requestDto) {
        List<DailyReportResponseDto> responseDto = companyRepository.getDailyReportsForMonth(requestDto.getCompanyId(),
                requestDto.getYear(), requestDto.getMonth());

        return responseDto;
    }

}
