package com.adisyon.adisyon_backend.Dto.Request.Product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDto {

    @NotNull
    private Long id;
}
