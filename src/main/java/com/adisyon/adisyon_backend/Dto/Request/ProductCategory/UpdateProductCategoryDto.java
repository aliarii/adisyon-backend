package com.adisyon.adisyon_backend.Dto.Request.ProductCategory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCategoryDto {
    @NotNull
    private Long id;

    @Nullable
    private String name;
}
