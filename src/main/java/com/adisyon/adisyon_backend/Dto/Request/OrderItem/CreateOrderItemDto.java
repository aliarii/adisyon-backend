package com.adisyon.adisyon_backend.Dto.Request.OrderItem;

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
}
