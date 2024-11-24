package com.adisyon.adisyon_backend.Controllers.Receipt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Receipt.CreateReceiptDto;
import com.adisyon.adisyon_backend.Entities.Receipt;
import com.adisyon.adisyon_backend.Services.Receipt.ReceiptService;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable Long id) {
        Receipt receipt = receiptService.findReceiptById(id);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Receipt>> getReceiptsByCompanyId(@PathVariable Long id) {
        List<Receipt> receipts = receiptService.findReceiptsByCompanyId(id);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @GetMapping("/{id}/{year}/{month}")
    public ResponseEntity<List<Receipt>> getReceiptsByMonth(@PathVariable Long id, @PathVariable Integer year,
            @PathVariable Integer month) {
        List<Receipt> receipts = receiptService.findReceiptsByMonth(id, year, month);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Receipt> createReceipt(@RequestBody CreateReceiptDto receiptDto) {
        Receipt receipt = receiptService.createReceipt(receiptDto);
        return new ResponseEntity<>(receipt, HttpStatus.CREATED);
    }

}
