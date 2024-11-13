package com.adisyon.adisyon_backend.Dto.Request.Owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOwnerDto {

    @NotEmpty(message = "Company Name is required")
    private String companyName;

    @NotEmpty(message = "Full Name is required")
    private String fullName;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters!")
    private String password;

}
