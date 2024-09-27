package com.adisyon.adisyon_backend.Services.ProductCategory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.CreateProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.DeleteProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.UpdateProductCategoryDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.ProductCategory;
import com.adisyon.adisyon_backend.Repositories.ProductCategory.ProductCategoryRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findProductCategoryById(Long id) {
        return Unwrapper.unwrap(productCategoryRepository.findById(id), id);
    }

    @Override
    public ProductCategory createProductCategory(CreateProductCategoryDto categoryDto, Company company) {

        ProductCategory newProductCategory = new ProductCategory();
        newProductCategory.setName(categoryDto.getName());
        newProductCategory.setCompany(company);
        newProductCategory.setIsActive(true);
        newProductCategory.setCreatedDate(new Date());

        ProductCategory savedProductCategory = productCategoryRepository.save(newProductCategory);
        company.getProductCategories().add(savedProductCategory);

        return savedProductCategory;
    }

    @Override
    public ProductCategory updateProductCategory(UpdateProductCategoryDto categoryDto) {
        ProductCategory existingProductCategory = findProductCategoryById(categoryDto.getId());
        existingProductCategory.setIsActive(false);
        productCategoryRepository.save(existingProductCategory);

        ProductCategory newProductCategory = new ProductCategory();
        newProductCategory.setName(
                categoryDto.getName() != null ? categoryDto.getName()
                        : existingProductCategory.getName());
        newProductCategory.setCompany(existingProductCategory.getCompany());
        newProductCategory.setIsActive(true);
        newProductCategory.setCreatedDate(new Date());

        ProductCategory savedProductCategory = productCategoryRepository.save(newProductCategory);
        existingProductCategory.getCompany().getProductCategories().add(savedProductCategory);

        return savedProductCategory;
    }

    @Override
    public void deleteProductCategory(DeleteProductCategoryDto categoryDto) {
        ProductCategory existingProductCategory = findProductCategoryById(categoryDto.getId());
        existingProductCategory.setIsActive(false);
        existingProductCategory.setUpdatedDate(new Date());
        productCategoryRepository.save(existingProductCategory);
    }

    @Override
    public List<ProductCategory> findProductCategoriesByCompanyId(Long id) {
        return productCategoryRepository.findByCompanyId(id);
    }

}
