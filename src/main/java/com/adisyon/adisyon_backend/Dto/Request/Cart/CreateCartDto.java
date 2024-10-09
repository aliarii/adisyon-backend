package com.adisyon.adisyon_backend.Dto.Request.Cart;

import com.adisyon.adisyon_backend.Entities.Basket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartDto {

    @NotNull
    private Basket basket;

}
