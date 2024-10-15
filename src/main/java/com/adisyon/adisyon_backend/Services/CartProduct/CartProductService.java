package com.adisyon.adisyon_backend.Services.CartProduct;

import com.adisyon.adisyon_backend.Dto.Request.CartProduct.CreateCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.DeleteCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.UpdateCartProductDto;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;

public interface CartProductService {
    public CartProduct findCartProductById(Long id);

    public CartProduct findCartProductByCartId(Long id);

    public Cart createCartProduct(CreateCartProductDto cartProductDto);

    public Cart updateCartProduct(UpdateCartProductDto cartProductDto);

    public Cart deleteCartProduct(DeleteCartProductDto cartProductDto);
}
