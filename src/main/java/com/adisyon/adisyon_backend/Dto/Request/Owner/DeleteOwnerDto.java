package com.adisyon.adisyon_backend.Dto.Request.Owner;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOwnerDto {

    @NotNull
    private Long id;
}
