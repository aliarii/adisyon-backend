package com.adisyon.adisyon_backend.Dto.Request.OrderTable;

import java.util.ArrayList;
import java.util.List;

import com.adisyon.adisyon_backend.Entities.OrderTableProduct;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderTableDto {

    @NotNull
    private Long orderTableId;

    @Nullable
    private String orderTableName;

    @Nullable
    private List<OrderTableProduct> orderTableProducts = new ArrayList<>();
}
