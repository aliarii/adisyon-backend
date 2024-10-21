package com.adisyon.adisyon_backend.Services.Payment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Payment.CreatePaymentDto;
import com.adisyon.adisyon_backend.Dto.Request.Payment.DeletePaymentDto;
import com.adisyon.adisyon_backend.Dto.Request.Payment.UpdatePaymentDto;
import com.adisyon.adisyon_backend.Entities.Payment;
import com.adisyon.adisyon_backend.Repositories.Payment.PaymentRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment findPaymentById(Long id) {
        return Unwrapper.unwrap(paymentRepository.findById(id), id);
    }

    @Override
    public Payment createPayment(CreatePaymentDto paymentDto) {
        Payment newPayment = new Payment();
        newPayment.setPaymentType(paymentDto.getPaymentType());
        newPayment.setPayAmount(paymentDto.getPayAmount());
        newPayment.setCompletedDate(new Date());
        return paymentRepository.save(newPayment);
    }

    @Override
    public Payment updatePayment(UpdatePaymentDto paymentDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePayment'");
    }

    @Override
    public Payment deletePayment(DeletePaymentDto paymentDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePayment'");
    }

}
