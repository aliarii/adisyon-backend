package com.adisyon.adisyon_backend.Dto.Request.CartProduct;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartProductDto {

    @NotNull
    private Long cartId;

    @NotNull
    private Long cartProductId;

    @NotNull
    private int quantity;
}
