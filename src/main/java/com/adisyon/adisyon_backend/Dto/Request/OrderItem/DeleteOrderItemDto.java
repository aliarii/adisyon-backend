package com.adisyon.adisyon_backend.Dto.Request.OrderItem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrderItemDto {
    @NotNull
    private Long id;
}
