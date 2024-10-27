package com.adisyon.adisyon_backend.Dto.Request.BasketCategory;

import java.util.ArrayList;
import java.util.List;

import com.adisyon.adisyon_backend.Entities.Basket;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBasketCategoryDto {

    @NotNull
    private String name;

    @NotNull
    private Long companyId;

    @Nullable
    private List<Basket> baskets = new ArrayList<>();
}
