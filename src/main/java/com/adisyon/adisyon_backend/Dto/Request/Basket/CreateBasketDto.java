package com.adisyon.adisyon_backend.Dto.Request.Basket;

import com.adisyon.adisyon_backend.Entities.Company;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBasketDto {

    @Nullable
    private String name;

    @NotNull
    private Company company;

}
