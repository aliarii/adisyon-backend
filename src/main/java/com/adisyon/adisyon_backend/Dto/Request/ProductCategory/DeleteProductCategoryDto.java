package com.adisyon.adisyon_backend.Dto.Request.ProductCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductCategoryDto {

    @NotNull
    private Long id;
}
