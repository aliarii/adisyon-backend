package com.adisyon.adisyon_backend.Dto.Request.Basket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBasketDto {

    @NotNull
    private Long id;

}
