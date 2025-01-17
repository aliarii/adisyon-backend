package com.adisyon.adisyon_backend.Dto.Request.Order;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.OrderItem;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDto {

    @NotNull
    private Long id;

    @Nullable
    private List<OrderItem> orderItems;
}
