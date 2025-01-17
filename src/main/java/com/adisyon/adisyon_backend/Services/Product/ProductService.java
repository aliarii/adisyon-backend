package com.adisyon.adisyon_backend.Services.Product;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Product.CreateProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.DeleteProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.UpdateProductDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Entities.ProductCategory;

public interface ProductService {
    public Product createProduct(CreateProductDto productDto, Company company, ProductCategory productCategory);

    public Product updateProduct(UpdateProductDto productDto);

    public void deleteProduct(DeleteProductDto productDto);

    public Product setProductCategory(UpdateProductDto productDto);

    public List<Product> findCompanyProducts(Long companyId, String productCategory);

    public Product findProductById(Long productId);

    public List<Product> searchProduct(String keyword);

    public Product updateProductStatus(Long productId);
}