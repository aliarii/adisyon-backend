package com.adisyon.adisyon_backend.Dto.Request.RecordItem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecordItemDto {
    @NotNull
    private Long id;
}
