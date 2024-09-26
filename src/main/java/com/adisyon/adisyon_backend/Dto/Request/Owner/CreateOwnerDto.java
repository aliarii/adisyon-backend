package com.adisyon.adisyon_backend.Dto.Request.Owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOwnerDto {

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters!")
    private String password;

}
