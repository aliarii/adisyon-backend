package com.adisyon.adisyon_backend.Services.Payment;

import com.adisyon.adisyon_backend.Dto.Request.Payment.CreatePaymentDto;
import com.adisyon.adisyon_backend.Dto.Request.Payment.DeletePaymentDto;
import com.adisyon.adisyon_backend.Dto.Request.Payment.UpdatePaymentDto;
import com.adisyon.adisyon_backend.Entities.Payment;

public interface PaymentService {
    public Payment findPaymentById(Long id);

    public Payment createPayment(CreatePaymentDto paymentDto);

    public Payment updatePayment(UpdatePaymentDto paymentDto);

    public Payment deletePayment(DeletePaymentDto paymentDto);
}
