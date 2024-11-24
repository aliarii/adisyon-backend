package com.adisyon.adisyon_backend.Controllers.BasketCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.CreateBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.DeleteBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.UpdateBasketCategoryDto;
import com.adisyon.adisyon_backend.Entities.BasketCategory;
import com.adisyon.adisyon_backend.Services.BasketCategory.BasketCategoryService;

@RestController
@RequestMapping("/api/basket/category")
public class BasketCategoryController {

    @Autowired
    private BasketCategoryService basketCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<BasketCategory> getBasketCategoryById(@PathVariable Long id) {
        BasketCategory basketCategories = basketCategoryService.findBasketCategoryById(id);
        return new ResponseEntity<>(basketCategories, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<BasketCategory>> getBasketCategoriesByCompanyId(@PathVariable Long id) {
        List<BasketCategory> basketCategories = basketCategoryService.findByCompanyId(id);
        return new ResponseEntity<>(basketCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BasketCategory> createBasketCategory(@RequestBody CreateBasketCategoryDto bCategoryDto) {
        BasketCategory basketCategory = basketCategoryService.createBasketCategory(bCategoryDto);
        return new ResponseEntity<>(basketCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BasketCategory> updateBasketCategory(@RequestBody UpdateBasketCategoryDto bCategoryDto) {
        BasketCategory basketCategory = basketCategoryService.updateBasketCategory(bCategoryDto);
        return new ResponseEntity<>(basketCategory, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<HttpStatus> deleteBasketCategory(@RequestBody DeleteBasketCategoryDto bCategoryDto) {
        basketCategoryService.deleteBasketCategory(bCategoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
