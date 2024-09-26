package com.adisyon.adisyon_backend.Dto.Request.Company;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDto {
    @NotEmpty
    @Size(min = 2)
    private String companyName;
}
