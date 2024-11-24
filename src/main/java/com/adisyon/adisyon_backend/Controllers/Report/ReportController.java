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

import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Services.Report.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/day/{id}/{date}")
    public ResponseEntity<DailyReportResponseDto> getReportByDay(@PathVariable Long id,
            @PathVariable LocalDate date) {
        DailyReportResponseDto responseDto = reportService.findReportByDay(id, date);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/month/{id}/{year}/{month}")
    public ResponseEntity<MonthlyReportResponseDto> getReportByMonth(@PathVariable Long id, @PathVariable Integer year,
            @PathVariable Integer month) {
        MonthlyReportResponseDto responseDto = reportService.findReportByMonth(id, year, month);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/year/{id}/{year}")
    public ResponseEntity<YearlyReportResponseDto> getReportByYear(@PathVariable Long id, @PathVariable Integer year) {
        YearlyReportResponseDto responseDto = reportService.findReportByYear(id, year);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/{year}/{month}")
    public ResponseEntity<List<DailyReportResponseDto>> getReportsByMonth(@PathVariable Long id,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        List<DailyReportResponseDto> responseDto = reportService.findReportsByMonth(id, year, month);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
