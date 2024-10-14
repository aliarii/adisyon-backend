package com.adisyon.adisyon_backend.Controllers.Cart;

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

import com.adisyon.adisyon_backend.Dto.Request.Cart.CreateCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.DeleteCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.UpdateCartDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.CreateCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.DeleteCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.UpdateCartProductDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.CartProduct.CartProductService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/basket/{id}")
    public ResponseEntity<Cart> getCartByBasketId(@PathVariable Long id) {
        Basket basket = basketService.findBasketById(id);
        Cart cart = cartService.findCartByBasketId(basket.getCart().getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartDto cartDto) {
        Cart cart = cartService.createCart(cartDto);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody UpdateCartDto cartDto) {
        Cart cart = cartService.updateCart(cartDto);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<HttpStatus> deleteCart(@RequestBody DeleteCartDto cartDto) {
        cartService.deleteCart(cartDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/clear/{id}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long id) {
        Cart cart = cartService.clearCart(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<Cart> createCartProduct(@RequestBody CreateCartProductDto productDto) {
        Cart cart = cartProductService.createCartProduct(productDto);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/product/update")
    public ResponseEntity<Cart> updateCartProduct(@RequestBody UpdateCartProductDto productDto) {
        Cart cart = cartProductService.updateCartProduct(productDto);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}/remove")
    public ResponseEntity<Cart> deleteCartProduct(@RequestBody DeleteCartProductDto productDto) {
        cartProductService.deleteCartProduct(productDto);
        Cart cart = cartService.findCartById(productDto.getCartId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
