package com.adisyon.adisyon_backend.Dto.Request.Owner;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOwnerDto {

    @NotNull
    private Long id;

    @Nullable
    private String fullName;

    @Nullable
    @Email
    private String email;

    @Nullable
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters!")
    private String password;

}