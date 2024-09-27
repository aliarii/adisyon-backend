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
public class CreateProductDto {

    @NotNull
    private String productName;

    @NotNull
    private Long price;

    @Nullable
    private ProductCategory productCategory;

    @Nullable
    private String image;

    @NotNull
    private Long companyId;

}
