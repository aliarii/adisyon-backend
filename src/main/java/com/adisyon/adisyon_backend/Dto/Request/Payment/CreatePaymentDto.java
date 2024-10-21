package com.adisyon.adisyon_backend.Dto.Request.Payment;

import com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDto {

    @NotNull
    private Long payAmount;

    @NotNull
    private PAYMENT_TYPE paymentType;
}
