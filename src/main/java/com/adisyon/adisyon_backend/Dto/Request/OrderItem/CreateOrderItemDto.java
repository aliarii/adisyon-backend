package com.adisyon.adisyon_backend.Dto.Request.OrderItem;

import com.adisyon.adisyon_backend.Entities.Order;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemDto {

    @NotNull
    private Long productId;

    @NotNull
    private int quantity;

    @Nullable
    private Order order;
}
