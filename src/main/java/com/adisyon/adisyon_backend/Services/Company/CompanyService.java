package com.adisyon.adisyon_backend.Services.Company;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Company.CreateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.DailyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.MonthlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.UpdateCompanyDto;
import com.adisyon.adisyon_backend.Dto.Request.Company.YearlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyService {
    public List<Company> findAllCompanies();

    public Company findCompanyById(Long id);

    public Company findCompanyByUserId(Long id);

    public Company findCompanyByEmployeeId(Long id);

    public Company createCompany(CreateCompanyDto companyDto, Long ownerId);

    public Company updateCompany(UpdateCompanyDto companyDto);

    // public void deleteCompany(DeleteCompanyDto companyDto);

    public DailyReportResponseDto getDailyReport(DailyReportRequestDto requestDto);

    public YearlyReportResponseDto getYearlyReport(YearlyReportRequestDto requestDto);

    public MonthlyReportResponseDto getMonthlyReport(MonthlyReportRequestDto requestDto);

    public List<DailyReportResponseDto> getDailyReportsForMonth(MonthlyReportRequestDto requestDto);

}
