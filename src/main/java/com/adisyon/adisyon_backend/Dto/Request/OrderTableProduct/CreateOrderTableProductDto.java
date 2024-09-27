package com.adisyon.adisyon_backend.Dto.Request.OrderTableProduct;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderTableProductDto {

    @NotNull
    private Long productId;
    @NotNull
    private int quantity;
}
