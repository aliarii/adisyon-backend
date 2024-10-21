package com.adisyon.adisyon_backend.Services.Receipt;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Receipt.CreateReceiptDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.DeleteReceiptDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.UpdateReceiptDto;
import com.adisyon.adisyon_backend.Entities.Receipt;

public interface ReceiptService {
    public Receipt createReceipt(CreateReceiptDto receiptDto);

    public Receipt updateReceipt(UpdateReceiptDto receiptDto);

    public void deleteReceipt(DeleteReceiptDto receiptDto);

    public List<Receipt> findReceiptsByCompanyId(Long id);

    public Receipt findReceiptById(Long id);
}
