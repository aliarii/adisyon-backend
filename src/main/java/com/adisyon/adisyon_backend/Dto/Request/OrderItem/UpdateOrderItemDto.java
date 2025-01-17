package com.adisyon.adisyon_backend.Dto.Request.OrderItem;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderItemDto {

    @NotNull
    private Long id;

    @Nullable
    private int quantity;
}
