package com.adisyon.adisyon_backend.Dto.Request.BasketProduct;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBasketProductDto {

    @NotNull
    private Long id;
}
