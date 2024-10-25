package com.adisyon.adisyon_backend.Dto.Request.Product;

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
    private String name;

    @NotNull
    private Long price;

    @Nullable
    private Long productCategoryId;

    @Nullable
    private String image;

    @NotNull
    private Long companyId;

}
