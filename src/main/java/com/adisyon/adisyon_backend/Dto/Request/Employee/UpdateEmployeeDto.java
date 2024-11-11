package com.adisyon.adisyon_backend.Dto.Request.Employee;

import java.util.ArrayList;
import java.util.List;

import com.adisyon.adisyon_backend.Entities.USER_PERMISSION;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDto {

    @NotNull
    private Long id;

    @Nullable
    private String fullName;

    @Nullable
    @Size(min = 2)
    private String userName;

    @Nullable
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters!")
    private String password;

    @Nullable
    private List<USER_PERMISSION> addedUserPermissions = new ArrayList<>();

    @Nullable
    private List<USER_PERMISSION> removedUserPermissions = new ArrayList<>();
}
