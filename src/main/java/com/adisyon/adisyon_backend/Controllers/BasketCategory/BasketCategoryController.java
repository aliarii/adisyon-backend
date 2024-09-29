package com.adisyon.adisyon_backend.Controllers.BasketCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/baskets/categories")
public class BasketCategoryController {

    @Autowired
    private BasketCategoryService basketCategoryService;

    @PostMapping("/create")
    public ResponseEntity<BasketCategory> createBasketCategory(@RequestBody CreateBasketCategoryDto bCategoryDto) {
        BasketCategory basketCategory = basketCategoryService.createBasketCategory(bCategoryDto);
        return new ResponseEntity<>(basketCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BasketCategory> updateBasketCategory(@RequestBody UpdateBasketCategoryDto bCategoryDto) {
        BasketCategory basketCategory = basketCategoryService.updateBasketCategory(bCategoryDto);
        return new ResponseEntity<>(basketCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteBasketCategory(@RequestBody DeleteBasketCategoryDto bCategoryDto) {
        basketCategoryService.deleteBasketCategory(bCategoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
