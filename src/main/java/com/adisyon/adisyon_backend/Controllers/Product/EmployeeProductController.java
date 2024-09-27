package com.adisyon.adisyon_backend.Controllers.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@RestController
@RequestMapping("/api/products")
public class EmployeeProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String name) {

        List<Product> products = productService.searchProduct(name);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Product>> findCompanyProducts(@RequestParam(required = false) String productCategory,
            @PathVariable Long companyId) {

        List<Product> products = productService.findCompanyProducts(companyId, productCategory);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
