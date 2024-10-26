package com.adisyon.adisyon_backend.Repositories.Company;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adisyon.adisyon_backend.Dto.Response.Company.DailyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.MonthlyReportResponseDto;
import com.adisyon.adisyon_backend.Dto.Response.Company.YearlyReportResponseDto;
import com.adisyon.adisyon_backend.Entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

        List<Company> findByIsActive(Boolean isActive);

        Optional<Company> findByOwnerId(Long userId);

        Optional<Company> findByEmployeesId(Long id);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Company.DailyReportResponseDto( " +
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
                        ":selectedDate AS date) "
                        +
                        "FROM Company c " +
                        "LEFT JOIN c.orders o " +
                        "LEFT JOIN o.payments p " +
                        "WHERE c.id = :companyId " +
                        "AND DATE(o.createdDate) = DATE(:selectedDate) " +
                        "GROUP BY c.id")

        DailyReportResponseDto getDailyReport(@Param("companyId") Long companyId,
                        @Param("selectedDate") LocalDate selectedDate);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Company.YearlyReportResponseDto( " +
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
        YearlyReportResponseDto getYearlyReport(@Param("companyId") Long companyId,
                        @Param("selectedYear") Integer selectedYear);

        @Query("SELECT new com.adisyon.adisyon_backend.Dto.Response.Company.MonthlyReportResponseDto( " +
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
        MonthlyReportResponseDto getMonthlyReport(@Param("companyId") Long companyId,
                        @Param("selectedYear") Integer selectedYear,
                        @Param("selectedMonth") Integer selectedMonth);

}
