package com.adisyon.adisyon_backend.Services.ProductCategory;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.CreateProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.DeleteProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.UpdateProductCategoryDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.ProductCategory;

public interface ProductCategoryService {

    public List<ProductCategory> findProductCategoriesByCompanyId(Long id);

    public ProductCategory findProductCategoryById(Long id);

    public ProductCategory createProductCategory(CreateProductCategoryDto categoryDto, Company company);

    public ProductCategory updateProductCategory(UpdateProductCategoryDto categoryDto);

    public void deleteProductCategory(DeleteProductCategoryDto categoryDto);

}
