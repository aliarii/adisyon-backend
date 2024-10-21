package com.adisyon.adisyon_backend.Dto.Request.Order;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.OrderItem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaySelectedOrdersDto {

    @NotNull
    private Long id;

    @NotNull
    private List<OrderItem> orderItems;

    @NotNull
    private String paymentType;
}
