package com.adisyon.adisyon_backend.Services.ProductCategory;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Product.UpdateProductDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.CreateProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.DeleteProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.UpdateProductCategoryDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Entities.ProductCategory;
import com.adisyon.adisyon_backend.Repositories.ProductCategory.ProductCategoryRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductService productService;

    @Override
    public ProductCategory findProductCategoryById(Long id) {
        return Unwrapper.unwrap(productCategoryRepository.findById(id), id);
    }

    @Override
    public List<ProductCategory> findProductCategoriesByCompanyId(Long id) {
        return productCategoryRepository.findByCompanyId(id);
    }

    @Override
    @Transactional
    public ProductCategory createProductCategory(CreateProductCategoryDto categoryDto, Company company) {

        ProductCategory newProductCategory = new ProductCategory();
        newProductCategory.setName(categoryDto.getName());
        newProductCategory.setCompany(company);
        newProductCategory.setIsActive(true);
        newProductCategory.setCreatedDate(LocalDateTime.now());
        ProductCategory savedProductCategory = productCategoryRepository.save(newProductCategory);

        for (Product product : categoryDto.getProducts()) {
            UpdateProductDto dto = new UpdateProductDto();
            dto.setId(product.getId());
            dto.setProductCategory(newProductCategory);
            productService.setProductCategory(dto);
        }
        company.getProductCategories().add(savedProductCategory);

        return savedProductCategory;
    }

    @Override
    @Transactional
    public ProductCategory updateProductCategory(UpdateProductCategoryDto categoryDto) {

        ProductCategory productCategory = findProductCategoryById(categoryDto.getId());

        productCategory
                .setName(categoryDto.getName() != null ? categoryDto.getName() : productCategory.getName());
        productCategory.setIsActive(
                categoryDto.getIsActive() != null ? categoryDto.getIsActive() : productCategory.getIsActive());

        for (Product addedProduct : categoryDto.getAddedProducts()) {
            Product product = productService.findProductById(addedProduct.getId());
            UpdateProductDto dto = new UpdateProductDto();
            dto.setId(product.getId());
            dto.setProductCategory(productCategory);
            productService.setProductCategory(dto);
        }
        for (Product removedProduct : categoryDto.getRemovedProducts()) {
            Product product = productService.findProductById(removedProduct.getId());
            if (product.getProductCategory() != productCategory)
                continue;
            productCategory.getProducts().remove(product);
            UpdateProductDto dto = new UpdateProductDto();
            dto.setId(product.getId());
            dto.setProductCategory(null);
            productService.setProductCategory(dto);
        }

        return productCategoryRepository.save(productCategory);
    }

    @Override
    @Transactional
    public void deleteProductCategory(DeleteProductCategoryDto categoryDto) {
        ProductCategory productCategory = findProductCategoryById(categoryDto.getId());
        productCategoryRepository.delete(productCategory);
    }
}
