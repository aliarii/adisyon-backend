package com.adisyon.adisyon_backend.Dto.Request.Order;

import com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayAmountDto {

    @NotNull
    private Long id;

    @NotNull
    private PAYMENT_TYPE paymentType;

    @NotNull
    private Long payAmount;
}
