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
public class TransferOrdersDto {

    @NotNull
    private Long id;

    @NotNull
    private Long basketId;

    @NotNull
    private List<OrderItem> orderItems;
}
