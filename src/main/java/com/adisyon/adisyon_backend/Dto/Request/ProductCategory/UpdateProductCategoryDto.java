package com.adisyon.adisyon_backend.Dto.Request.ProductCategory;

import java.util.ArrayList;
import java.util.List;

import com.adisyon.adisyon_backend.Entities.Product;

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

    @Nullable
    private Boolean isActive;

    @Nullable
    private List<Product> addedProducts = new ArrayList<>();

    @Nullable
    private List<Product> removedProducts = new ArrayList<>();
}
