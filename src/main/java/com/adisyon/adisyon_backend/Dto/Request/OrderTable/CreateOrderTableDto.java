package com.adisyon.adisyon_backend.Dto.Request.OrderTable;

import com.adisyon.adisyon_backend.Entities.Company;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderTableDto {

    @Nullable
    private String orderTableName;

    @NotNull
    private Company company;

}
