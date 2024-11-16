package com.adisyon.adisyon_backend.Controllers.Report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Report.DailyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.MonthlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Report.YearlyReportRequestDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Services.Report.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/daily/{id}/{date}")
    public ResponseEntity<DailyReportResponseDto> getDailyReport(@PathVariable Long id,
            @PathVariable LocalDate date) {

        DailyReportRequestDto requestDto = new DailyReportRequestDto(id, date);
        DailyReportResponseDto responseDto = reportService.getDailyReport(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/monthly/{id}/{year}/{month}")
    public ResponseEntity<MonthlyReportResponseDto> getMonthlyReport(@PathVariable Long id, @PathVariable Integer year,
            @PathVariable Integer month) {
        MonthlyReportRequestDto requestDto = new MonthlyReportRequestDto(id, month, year);
        MonthlyReportResponseDto responseDto = reportService.getMonthlyReport(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/yearly/{id}/{year}")
    public ResponseEntity<YearlyReportResponseDto> getYearlyReport(@PathVariable Long id, @PathVariable Integer year) {
        YearlyReportRequestDto requestDto = new YearlyReportRequestDto(id, year);
        YearlyReportResponseDto responseDto = reportService.getYearlyReport(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/dailyForMonth/{id}/{year}/{month}")
    public ResponseEntity<List<DailyReportResponseDto>> getDailyReportsForMonth(@PathVariable Long id,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        MonthlyReportRequestDto requestDto = new MonthlyReportRequestDto(id, month, year);
        List<DailyReportResponseDto> responseDto = reportService.getDailyReportsForMonth(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
