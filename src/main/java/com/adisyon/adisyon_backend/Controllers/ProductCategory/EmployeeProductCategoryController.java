package com.adisyon.adisyon_backend.Controllers.ProductCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Entities.ProductCategory;
import com.adisyon.adisyon_backend.Services.ProductCategory.ProductCategoryService;

@RestController
@RequestMapping("/api/employee/productCategories")
public class EmployeeProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/company/{id}")
    public ResponseEntity<List<ProductCategory>> findProductCategoriesByCompanyId(@PathVariable Long id) {

        List<ProductCategory> productCategories = productCategoryService.findProductCategoriesByCompanyId(id);
        return new ResponseEntity<>(productCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> findProductCategoryById(@PathVariable Long id) {
        ProductCategory productCategory = productCategoryService.findProductCategoryById(id);
        return new ResponseEntity<>(productCategory, HttpStatus.OK);
    }

}
