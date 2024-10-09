package com.adisyon.adisyon_backend.Dto.Request.Basket;

import com.adisyon.adisyon_backend.Entities.BasketCategory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBasketDto {

    @NotNull
    private Long id;

    @Nullable
    private String name;

    @Nullable
    private BasketCategory basketCategory;
}
