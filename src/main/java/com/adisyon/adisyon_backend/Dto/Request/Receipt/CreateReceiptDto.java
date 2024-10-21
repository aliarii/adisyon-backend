package com.adisyon.adisyon_backend.Dto.Request.Receipt;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReceiptDto {

    @NotNull
    private Long companyId;

    @NotNull
    private Basket basket;

    @NotNull
    private List<Order> orders;
}
