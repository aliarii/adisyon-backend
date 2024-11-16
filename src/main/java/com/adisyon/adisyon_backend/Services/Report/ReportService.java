package com.adisyon.adisyon_backend.Services.Report;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Report.DailyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.MonthlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.YearlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;

public interface ReportService {
    public DailyReportResponseDto getDailyReport(DailyReportRequestDto requestDto);

    public YearlyReportResponseDto getYearlyReport(YearlyReportRequestDto requestDto);

    public MonthlyReportResponseDto getMonthlyReport(MonthlyReportRequestDto requestDto);

    public List<DailyReportResponseDto> getDailyReportsForMonth(MonthlyReportRequestDto requestDto);
}
