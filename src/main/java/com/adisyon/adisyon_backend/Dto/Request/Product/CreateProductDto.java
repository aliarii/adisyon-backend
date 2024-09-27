package com.adisyon.adisyon_backend.Dto.Request.Product;

import com.adisyon.adisyon_backend.Entities.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {

    private String productName;

    private Long price;

    private ProductCategory productCategory;

    private String image;

    private Long companyId;

}
