package com.adisyon.adisyon_backend.Dto.Request.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCartDto {

    @NotNull
    private Long id;
}
