package com.adisyon.adisyon_backend.Controllers.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.CreateProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.DeleteProductCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.ProductCategory.UpdateProductCategoryDto;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.ProductCategory;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.ProductCategory.ProductCategoryService;

@RestController
@RequestMapping("/api/owner/productCategories")
public class OwnerProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ProductCategory> createProduct(@RequestBody CreateProductCategoryDto categoryDto) {
        Company company = companyService.findCompanyById(categoryDto.getCompanyId());
        ProductCategory productCategory = productCategoryService.createProductCategory(categoryDto, company);
        return new ResponseEntity<>(productCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductCategory> updateProduct(@RequestBody UpdateProductCategoryDto categoryDto) {
        ProductCategory productCategory = productCategoryService.updateProductCategory(categoryDto);
        return new ResponseEntity<>(productCategory, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteProductCategoryDto categoryDto) {
        productCategoryService.deleteProductCategory(categoryDto);
        String res = "product deleted successfully";
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
