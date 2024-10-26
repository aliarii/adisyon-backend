package com.adisyon.adisyon_backend.Dto.Request.Company;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportRequestDto {
    Long companyId;
    LocalDate date;
}
