package com.adisyon.adisyon_backend.Dto.Request.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderDto {
    @NotNull
    private Long id;
}
