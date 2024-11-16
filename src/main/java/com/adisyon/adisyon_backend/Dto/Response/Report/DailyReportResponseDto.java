package com.adisyon.adisyon_backend.Dto.Response.Report;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyReportResponseDto {

    private Long cashPaidTotal;
    private Long creditCardPaidTotal;
    private Long pendingOrdersTotalPrice;
    private Long pendingOrdersCount;
    private Long completedOrdersCount;
    private Long paidTotal;
    private LocalDate date;

    public DailyReportResponseDto(Long cashPaidTotal, Long creditCardPaidTotal, Long pendingOrdersTotalPrice,
            Long pendingOrdersCount, Long completedOrdersCount, Long paidTotal, Date sqlDate, LocalDate localDate) {
        this.cashPaidTotal = cashPaidTotal;
        this.creditCardPaidTotal = creditCardPaidTotal;
        this.pendingOrdersTotalPrice = pendingOrdersTotalPrice;
        this.pendingOrdersCount = pendingOrdersCount;
        this.completedOrdersCount = completedOrdersCount;
        this.paidTotal = paidTotal;
        this.date = sqlDate != null ? sqlDate.toLocalDate() : localDate;
    }

}
