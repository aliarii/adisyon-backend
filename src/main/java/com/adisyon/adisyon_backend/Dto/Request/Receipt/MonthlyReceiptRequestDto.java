package com.adisyon.adisyon_backend.Dto.Request.Receipt;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReceiptRequestDto {
    @NotNull
    Long companyId;

    @NotNull
    Integer month;

    @NotNull
    Integer year;
}
