package com.adisyon.adisyon_backend.Services.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Product.CreateProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.DeleteProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.UpdateProductDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Repositories.Product.ProductRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findProductById(Long productId) {
        return Unwrapper.unwrap(productRepository.findById(productId), productId);
    }

    @Override
    public Product createProduct(CreateProductDto productDto, Company company) {

        Product newProduct = new Product();
        newProduct.setName(productDto.getName());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setProductCategory(productDto.getProductCategory());
        newProduct.setCompany(company);
        newProduct.setImage(productDto.getImage());
        newProduct.setIsActive(true);
        newProduct.setCreatedDate(LocalDateTime.now());

        Product savedProduct = productRepository.save(newProduct);
        company.getProducts().add(savedProduct);

        return savedProduct;
    }

    @Override
    public Product updateProduct(UpdateProductDto productDto) {

        Product existingProduct = findProductById(productDto.getId());
        existingProduct.setIsActive(false);
        productRepository.save(existingProduct);

        Product newProduct = new Product();
        newProduct.setName(
                productDto.getName() != null ? productDto.getName() : existingProduct.getName());
        newProduct.setPrice(productDto.getPrice() != null ? productDto.getPrice() : existingProduct.getPrice());
        newProduct.setProductCategory(productDto.getProductCategory() != null ? productDto.getProductCategory()
                : existingProduct.getProductCategory());
        newProduct.setIsActive(
                productDto.getIsActive() != null ? productDto.getIsActive() : existingProduct.getIsActive());
        newProduct.setImage(productDto.getImage() != null ? productDto.getImage() : existingProduct.getImage());

        newProduct.setCompany(existingProduct.getCompany());
        newProduct.setCreatedDate(existingProduct.getCreatedDate());
        newProduct.setUpdatedDate(LocalDateTime.now());

        Product savedProduct = productRepository.save(newProduct);
        existingProduct.getCompany().getProducts().add(savedProduct);
        return savedProduct;
    }

    @Override
    public void deleteProduct(DeleteProductDto productDto) {
        Product product = findProductById(productDto.getId());
        product.setIsActive(false);
        product.setUpdatedDate(LocalDateTime.now());
        productRepository.save(product);
    }

    @Override
    public Product setProductCategory(UpdateProductDto productDto) {
        Product product = findProductById(productDto.getId());
        product.setProductCategory(productDto.getProductCategory());
        return productRepository.save(product);
    }

    @Override
    public List<Product> findCompanyProducts(Long companyId, String productCategory) {

        List<Product> products = productRepository.findByCompanyId(companyId);

        if (productCategory != null && !productCategory.equals(""))
            products = filterByCategory(products, productCategory);
        return products;
    }

    private List<Product> filterByCategory(List<Product> products, String productCategory) {
        return products.stream().filter(product -> {
            if (product.getProductCategory() != null)
                return product.getProductCategory().getName().equals(productCategory);

            return false;
        }).collect(Collectors.toList());

    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return productRepository.searchProduct(keyword);
    }

    @Override
    public Product updateProductStatus(Long productId) {
        Product product = findProductById(productId);
        product.setIsActive(!product.getIsActive());
        return productRepository.save(product);
    }
}