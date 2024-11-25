package com.adisyon.adisyon_backend.Dto.Response.Report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyReportResponseDto {
    private Long cashPaidTotal;
    private Long creditCardPaidTotal;
    private Long pendingOrdersTotalPrice;
    private Long pendingOrdersCount;
    private Long completedOrdersCount;
    private Long paidTotal;
    private Integer year;
}