package com.adisyon.adisyon_backend.Dto.Request.Employee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteEmployeeDto {

    @NotNull
    private Long id;
}
