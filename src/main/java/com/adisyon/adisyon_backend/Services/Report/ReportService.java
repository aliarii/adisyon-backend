package com.adisyon.adisyon_backend.Services.Report;

import java.time.LocalDate;
import java.util.List;

import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;

public interface ReportService {
    public DailyReportResponseDto findReportByDay(Long id, LocalDate date);

    public YearlyReportResponseDto findReportByYear(Long id, Integer year);

    public MonthlyReportResponseDto findReportByMonth(Long id, Integer year, Integer month);

    public List<DailyReportResponseDto> findReportsByMonth(Long id, Integer year, Integer month);
}
