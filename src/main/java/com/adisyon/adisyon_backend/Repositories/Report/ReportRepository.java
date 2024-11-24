package com.adisyon.adisyon_backend.Repositories.Report;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Entities.Company;

public interface ReportRepository extends JpaRepository<Company, Long> {

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto( " +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CASH THEN p.payAmount ELSE 0 END) AS cashPaidTotal,"
                        +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CREDIT_CARD THEN p.payAmount ELSE 0 END) AS creditCardPaidTotal,"
                        +
                        "SUM(CASE WHEN o.completedDate IS NULL THEN o.totalPrice ELSE 0 END) AS pendingOrdersTotalPrice,"
                        +
                        "COUNT(CASE WHEN o.completedDate IS NULL THEN o.id ELSE null END) AS pendingOrdersCount,"
                        +
                        "COUNT(CASE WHEN o.completedDate IS NOT NULL THEN o.id ELSE null END) AS completedOrdersCount,"
                        +
                        "SUM(CASE WHEN p.payAmount IS NOT NULL THEN p.payAmount ELSE 0 END) AS paidTotal, "
                        +
                        "null AS date, "
                        +
                        ":selectedDate AS localDate) "
                        +
                        "FROM Company c " +
                        "LEFT JOIN c.orders o " +
                        "LEFT JOIN o.payments p " +
                        "WHERE c.id = :companyId " +
                        "AND DATE(o.createdDate) = DATE(:selectedDate) " +
                        "GROUP BY c.id")

        DailyReportResponseDto findReportByDay(@Param("companyId") Long companyId,
                        @Param("selectedDate") LocalDate selectedDate);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Report.YearlyReportResponseDto( " +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CASH THEN p.payAmount ELSE 0 END) AS cashPaidTotal, "
                        +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CREDIT_CARD THEN p.payAmount ELSE 0 END) AS creditCardPaidTotal, "
                        +
                        "SUM(CASE WHEN o.completedDate IS NULL THEN o.totalPrice ELSE 0 END) AS pendingOrdersTotalPrice, "
                        +
                        "COUNT(CASE WHEN o.completedDate IS NULL THEN o.id ELSE null END) AS pendingOrdersCount, " +
                        "COUNT(CASE WHEN o.completedDate IS NOT NULL THEN o.id ELSE null END) AS completedOrdersCount, "
                        +
                        "SUM(CASE WHEN p.payAmount IS NOT NULL THEN p.payAmount ELSE 0 END) AS paidTotal, " +
                        ":selectedYear AS year) " +
                        "FROM Company c " +
                        "LEFT JOIN c.orders o " +
                        "LEFT JOIN o.payments p " +
                        "WHERE c.id = :companyId " +
                        "AND YEAR(o.createdDate) = :selectedYear " +
                        "GROUP BY c.id")
        YearlyReportResponseDto findReportByYear(@Param("companyId") Long companyId,
                        @Param("selectedYear") Integer selectedYear);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Report.MonthlyReportResponseDto( " +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CASH THEN p.payAmount ELSE 0 END) AS cashPaidTotal, "
                        +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CREDIT_CARD THEN p.payAmount ELSE 0 END) AS creditCardPaidTotal, "
                        +
                        "SUM(CASE WHEN o.completedDate IS NULL THEN o.totalPrice ELSE 0 END) AS pendingOrdersTotalPrice, "
                        +
                        "COUNT(CASE WHEN o.completedDate IS NULL THEN o.id ELSE null END) AS pendingOrdersCount, " +
                        "COUNT(CASE WHEN o.completedDate IS NOT NULL THEN o.id ELSE null END) AS completedOrdersCount, "
                        +
                        "SUM(CASE WHEN p.payAmount IS NOT NULL THEN p.payAmount ELSE 0 END) AS paidTotal, " +
                        ":selectedMonth AS month, " +
                        ":selectedYear AS year) " +
                        "FROM Company c " +
                        "LEFT JOIN c.orders o " +
                        "LEFT JOIN o.payments p " +
                        "WHERE c.id = :companyId " +
                        "AND YEAR(o.createdDate) = :selectedYear " +
                        "AND MONTH(o.createdDate) = :selectedMonth " +
                        "GROUP BY c.id")
        MonthlyReportResponseDto findReportByMonth(@Param("companyId") Long companyId,
                        @Param("selectedYear") Integer selectedYear,
                        @Param("selectedMonth") Integer selectedMonth);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Report.DailyReportResponseDto( " +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CASH THEN p.payAmount ELSE 0 END) AS cashPaidTotal, "
                        +
                        "SUM(CASE WHEN p.paymentType = com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE.TYPE_CREDIT_CARD THEN p.payAmount ELSE 0 END) AS creditCardPaidTotal, "
                        +
                        "SUM(CASE WHEN o.completedDate IS NULL THEN o.totalPrice ELSE 0 END) AS pendingOrdersTotalPrice, "
                        +
                        "COUNT(CASE WHEN o.completedDate IS NULL THEN o.id ELSE null END) AS pendingOrdersCount, " +
                        "COUNT(CASE WHEN o.completedDate IS NOT NULL THEN o.id ELSE null END) AS completedOrdersCount, "
                        +
                        "SUM(COALESCE(p.payAmount, 0)) AS paidTotal, " +
                        "DATE(o.createdDate) AS date, " +
                        "null AS localDate) " +
                        "FROM Company c " +
                        "LEFT JOIN c.orders o " +
                        "LEFT JOIN o.payments p " +
                        "WHERE c.id = :companyId " +
                        "AND YEAR(o.createdDate) = :selectedYear " +
                        "AND MONTH(o.createdDate) = :selectedMonth " +
                        "GROUP BY DATE(o.createdDate) " +
                        "ORDER BY DATE(o.createdDate)")
        List<DailyReportResponseDto> findReportsByMonth(@Param("companyId") Long companyId,
                        @Param("selectedYear") Integer selectedYear,
                        @Param("selectedMonth") Integer selectedMonth);
}
