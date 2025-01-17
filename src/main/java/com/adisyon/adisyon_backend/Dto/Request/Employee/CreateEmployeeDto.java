package com.adisyon.adisyon_backend.Dto.Request.Employee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

    @NotEmpty
    private String fullName;

    @NotEmpty
    @Size(min = 2)
    private String userName;

    @NotNull
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters!")
    private String password;

    @NotNull
    private Long companyId;

}
