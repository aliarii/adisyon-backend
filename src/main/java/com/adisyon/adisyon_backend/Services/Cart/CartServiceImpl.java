package com.adisyon.adisyon_backend.Services.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Cart.CreateCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.DeleteCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.UpdateCartDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Repositories.Cart.CartRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart findCartById(Long id) {
        return Unwrapper.unwrap(cartRepository.findById(id), id);
    }

    @Override
    public Cart findCartByBasketId(Long id) {
        Cart cart = cartRepository.findByBasketId(id);
        return cart;
    }

    @Override
    public Cart createCart(CreateCartDto cartDto) {
        Basket basket = cartDto.getBasket();
        Cart newCart = new Cart();
        newCart.setBasket(basket);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart updateCart(UpdateCartDto cartDto) {
        return null;
    }

    @Override
    public void deleteCart(DeleteCartDto cartDto) {
        Cart cart = findCartById(cartDto.getId());
        cartRepository.delete(cart);
    }

    @Override
    public Cart clearCart(Long id) {
        Cart cart = findCartById(id);
        cart.getCartProducts().clear();
        return cartRepository.save(cart);
    }
}
