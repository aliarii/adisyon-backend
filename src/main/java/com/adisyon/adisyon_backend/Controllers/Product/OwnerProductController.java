package com.adisyon.adisyon_backend.Controllers.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Product.CreateProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.DeleteProductDto;
import com.adisyon.adisyon_backend.Dto.Request.Product.UpdateProductDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@RestController
@RequestMapping("/api/owner/products")
public class OwnerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto productDto) {

        Company company = companyService.findCompanyById(productDto.getCompanyId());
        Product product = productService.createProduct(productDto, company);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductDto productDto) {
        Product product = productService.updateProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteProductDto productDto) {
        productService.deleteProduct(productDto);
        String res = "product deleted successfully";
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Product> updateProductStatus(@PathVariable Long id) {
        Product product = productService.updateProductStatus(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
