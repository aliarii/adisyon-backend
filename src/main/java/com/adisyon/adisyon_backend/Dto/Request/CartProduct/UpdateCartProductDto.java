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
    private Long id;

    @NotNull
    private int quantity;
}
