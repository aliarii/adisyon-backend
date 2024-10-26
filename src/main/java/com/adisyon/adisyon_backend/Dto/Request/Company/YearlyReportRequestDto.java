package com.adisyon.adisyon_backend.Dto.Request.Company;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyReportRequestDto {

    @NotNull
    Long companyId;

    @NotNull
    Integer year;
}
