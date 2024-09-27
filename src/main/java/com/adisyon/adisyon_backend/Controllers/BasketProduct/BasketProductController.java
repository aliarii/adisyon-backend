package com.adisyon.adisyon_backend.Controllers.BasketProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.CreateBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.DeleteBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.UpdateBasketProductDto;
import com.adisyon.adisyon_backend.Entities.BasketProduct;
import com.adisyon.adisyon_backend.Services.BasketProduct.BasketProductService;

@RestController
@RequestMapping("/api/basketProducts")
public class BasketProductController {

    @Autowired
    private BasketProductService basketProductService;

    @PostMapping("/create")
    public ResponseEntity<BasketProduct> createBasketProduct(@RequestBody CreateBasketProductDto bProductDto) {
        BasketProduct basketProduct = basketProductService.createBasketProduct(bProductDto);
        return new ResponseEntity<>(basketProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BasketProduct> updateBasketProduct(@RequestBody UpdateBasketProductDto bProductDto) {
        BasketProduct basketProduct = basketProductService.updateBasketProduct(bProductDto);
        return new ResponseEntity<>(basketProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteBasketProduct(@RequestBody DeleteBasketProductDto bProductDto) {
        basketProductService.deleteBasketProduct(bProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
