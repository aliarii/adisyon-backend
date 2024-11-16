package com.adisyon.adisyon_backend.Services.Receipt;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Receipt.CreateReceiptDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.DeleteReceiptDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.MonthlyReceiptRequestDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.UpdateReceiptDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Order;
import com.adisyon.adisyon_backend.Entities.Receipt;
import com.adisyon.adisyon_backend.Repositories.Receipt.ReceiptRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public Receipt findReceiptById(Long id) {
        return Unwrapper.unwrap(receiptRepository.findById(id), id);
    }

    @Override
    public List<Receipt> findReceiptsByCompanyId(Long id) {
        return receiptRepository.findByCompanyId(id);
    }

    @Override
    public Receipt createReceipt(CreateReceiptDto receiptDto) {
        Company company = companyService.findCompanyById(receiptDto.getCompanyId());
        Basket basket = receiptDto.getBasket();

        Receipt newReceipt = new Receipt();

        newReceipt.setBasketId(basket.getId());
        newReceipt.setBasketName(basket.getName());
        newReceipt.setBasketOpenDate(basket.getActivatedDate());
        newReceipt.setBasketCloseDate(basket.getDeactivatedDate());
        newReceipt.setCompany(company);
        newReceipt.setCreatedDate(LocalDateTime.now());
        newReceipt.getOrders().addAll(receiptDto.getOrders());
        newReceipt.setTotalPrice(newReceipt.getOrders().stream().mapToLong(Order::getTotalPrice).sum());
        return receiptRepository.save(newReceipt);
    }

    @Override
    public Receipt updateReceipt(UpdateReceiptDto receiptDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReceipt'");
    }

    @Override
    public void deleteReceipt(DeleteReceiptDto receiptDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteReceipt'");
    }

    @Override
    @Transactional
    public List<Receipt> getMonthlyReceipts(MonthlyReceiptRequestDto receiptDto) {

        YearMonth yearMonth = YearMonth.of(receiptDto.getYear(), receiptDto.getMonth());
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return receiptRepository.getMonthlyReceipts(receiptDto.getCompanyId(), startOfMonth, endOfMonth);
    }

}
