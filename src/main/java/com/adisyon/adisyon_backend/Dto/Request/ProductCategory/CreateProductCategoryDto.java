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
public class CreateProductCategoryDto {

    @NotNull
    private String name;

    @NotNull
    private Long companyId;

    @Nullable
    private List<Product> products = new ArrayList<>();
}
