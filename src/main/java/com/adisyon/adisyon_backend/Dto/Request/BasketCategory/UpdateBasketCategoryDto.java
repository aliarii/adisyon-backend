package com.adisyon.adisyon_backend.Dto.Request.BasketCategory;

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

    @NotNull
    private String name;
}