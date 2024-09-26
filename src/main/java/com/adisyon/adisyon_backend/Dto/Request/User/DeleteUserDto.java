package com.adisyon.adisyon_backend.Dto.Request.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {

    @NotNull
    private Long id;
}
