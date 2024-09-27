package com.adisyon.adisyon_backend.Dto.Request.Product;

import com.adisyon.adisyon_backend.Entities.ProductCategory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {

    @NotNull
    private Long id;

    @Nullable
    private String name;

    @Nullable
    private Long price;

    @Nullable
    private ProductCategory productCategory;

    @Nullable
    private Boolean isActive;

    @Nullable
    private String image;
}
