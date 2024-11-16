package com.adisyon.adisyon_backend.Services.Report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Report.DailyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.MonthlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.YearlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Repositories.Report.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    @Transactional
    public DailyReportResponseDto getDailyReport(DailyReportRequestDto requestDto) {

        DailyReportResponseDto responseDto = reportRepository.getDailyReport(requestDto.getCompanyId(),
                requestDto.getDate());
        if (responseDto == null)
            responseDto = new DailyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, null, requestDto.getDate());

        return responseDto;
    }

    @Override
    @Transactional
    public YearlyReportResponseDto getYearlyReport(YearlyReportRequestDto requestDto) {

        YearlyReportResponseDto responseDto = reportRepository.getYearlyReport(requestDto.getCompanyId(),
                requestDto.getYear());
        if (responseDto == null)
            responseDto = new YearlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, requestDto.getYear());

        return responseDto;
    }

    @Override
    @Transactional
    public MonthlyReportResponseDto getMonthlyReport(MonthlyReportRequestDto requestDto) {

        MonthlyReportResponseDto responseDto = reportRepository.getMonthlyReport(requestDto.getCompanyId(),
                requestDto.getYear(), requestDto.getMonth());
        if (responseDto == null)
            responseDto = new MonthlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, requestDto.getMonth(),
                    requestDto.getYear());

        return responseDto;
    }

    @Override
    @Transactional
    public List<DailyReportResponseDto> getDailyReportsForMonth(MonthlyReportRequestDto requestDto) {
        List<DailyReportResponseDto> responseDto = reportRepository.getDailyReportsForMonth(requestDto.getCompanyId(),
                requestDto.getYear(), requestDto.getMonth());

        return responseDto;
    }
}
