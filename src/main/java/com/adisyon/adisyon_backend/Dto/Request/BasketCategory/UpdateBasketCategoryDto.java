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
public class UpdateBasketCategoryDto {

    @NotNull
    private Long id;

    @Nullable
    private String name;

    @Nullable
    private List<Basket> addedBaskets = new ArrayList<>();

    private List<Basket> removedBaskets = new ArrayList<>();
}
