package com.adisyon.adisyon_backend.Dto.Response.Company;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportResponseDto {

    private Long cashPaidTotal;
    private Long creditCardPaidTotal;
    private Long pendingOrdersTotalPrice;
    private Long pendingOrdersCount;
    private Long completedOrdersCount;
    private Long paidTotal;
    private LocalDate date;

}
