package com.adisyon.adisyon_backend.Controllers.Basket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.adisyon.adisyon_backend.Services.Basket.BasketService;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<List<Basket>> getAllBaskets(@PathVariable Long id) {
        List<Basket> baskets = basketService.findByCompanyId(id);
        return new ResponseEntity<>(baskets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        Basket basket = basketService.findBasketById(id);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody CreateBasketDto basketDto) {
        Basket newBasket = basketService.createBasket(basketDto);
        return new ResponseEntity<>(newBasket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@RequestBody UpdateBasketDto basketDto) {
        Basket updatedBasket = basketService.updateBasket(basketDto);
        return new ResponseEntity<>(updatedBasket, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBasket(@RequestBody DeleteBasketDto basketDto) {
        basketService.deleteBasket(basketDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Basket> activateBasket(@PathVariable Long id) {
        Basket basket = basketService.activateBasket(id);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> disableBasket(@PathVariable Long id) {
        basketService.disableBasket(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
