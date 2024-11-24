package com.adisyon.adisyon_backend.Services.Report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public DailyReportResponseDto findReportByDay(Long id, LocalDate date) {

        DailyReportResponseDto responseDto = reportRepository.findReportByDay(id, date);
        if (responseDto == null)
            responseDto = new DailyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, null, date);

        return responseDto;
    }

    @Override
    @Transactional
    public YearlyReportResponseDto findReportByYear(Long id, Integer year) {

        YearlyReportResponseDto responseDto = reportRepository.findReportByYear(id, year);
        if (responseDto == null)
            responseDto = new YearlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, year);

        return responseDto;
    }

    @Override
    @Transactional
    public MonthlyReportResponseDto findReportByMonth(Long id, Integer year, Integer month) {

        MonthlyReportResponseDto responseDto = reportRepository.findReportByMonth(id, year, month);
        if (responseDto == null)
            responseDto = new MonthlyReportResponseDto(0L, 0L, 0L, 0L, 0L, 0L, month,
                    year);

        return responseDto;
    }

    @Override
    @Transactional
    public List<DailyReportResponseDto> findReportsByMonth(Long id, Integer year, Integer month) {
        List<DailyReportResponseDto> responseDto = reportRepository.findReportsByMonth(id, year, month);

        return responseDto;
    }
}
