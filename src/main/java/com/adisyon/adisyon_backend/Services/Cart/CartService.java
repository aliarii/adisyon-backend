package com.adisyon.adisyon_backend.Services.Cart;

import com.adisyon.adisyon_backend.Dto.Request.Cart.CreateCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.DeleteCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.UpdateCartDto;
import com.adisyon.adisyon_backend.Entities.Cart;

public interface CartService {
    public Cart findCartById(Long id);

    public Cart findCartByBasketId(Long id);

    public Cart createCart(CreateCartDto cartDto);

    public Cart updateCart(UpdateCartDto cartDto);

    public void deleteCart(DeleteCartDto cartDto);

    public Cart clearCart(Long id);
}
