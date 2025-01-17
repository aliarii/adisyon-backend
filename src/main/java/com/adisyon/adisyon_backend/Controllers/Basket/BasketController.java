package com.adisyon.adisyon_backend.Controllers.Basket;

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

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.DeleteBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.BasketCategory;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.BasketCategory.BasketCategoryService;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private BasketCategoryService basketCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        Basket basket = basketService.findBasketById(id);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Basket>> getBasketsByCompanyId(@PathVariable Long id) {
        List<Basket> baskets = basketService.findByCompanyId(id);
        return new ResponseEntity<>(baskets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody CreateBasketDto basketDto) {
        BasketCategory basketCategory = null;
        if (basketDto.getBasketCategoryId() != null)
            basketCategory = basketCategoryService.findBasketCategoryById(basketDto.getBasketCategoryId());
        Basket newBasket = basketService.createBasket(basketDto, basketCategory);
        return new ResponseEntity<>(newBasket, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Basket> updateBasket(@RequestBody UpdateBasketDto basketDto) {
        Basket updatedBasket = basketService.updateBasket(basketDto);
        return new ResponseEntity<>(updatedBasket, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<HttpStatus> deleteBasket(@RequestBody DeleteBasketDto basketDto) {
        basketService.deleteBasket(basketDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Basket> activateBasket(@PathVariable Long id) {
        Basket basket = basketService.activateBasket(id);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Basket> deactivateBasket(@PathVariable Long id) {
        Basket basket = basketService.deactivateBasket(id);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }
}
