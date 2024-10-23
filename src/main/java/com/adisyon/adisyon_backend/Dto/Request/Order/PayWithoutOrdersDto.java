package com.adisyon.adisyon_backend.Dto.Request.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayWithoutOrdersDto {

    @NotNull
    private Long id;

    @NotNull
    private String paymentType;

    @NotNull
    private Long payAmount;
}
