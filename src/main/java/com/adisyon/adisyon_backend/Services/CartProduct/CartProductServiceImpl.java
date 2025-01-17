package com.adisyon.adisyon_backend.Services.CartProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.CartProduct.CreateCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.DeleteCartProductDto;
import com.adisyon.adisyon_backend.Dto.Request.CartProduct.UpdateCartProductDto;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Repositories.CartProduct.CartProductRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@Service
public class CartProductServiceImpl implements CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Override
    public CartProduct findCartProductById(Long id) {
        return Unwrapper.unwrap(cartProductRepository.findById(id), id);

    }

    @Override
    public CartProduct findCartProductByCartId(Long id) {
        return cartProductRepository.findByCartId(id);
    }

    @Override
    public Cart createCartProduct(CreateCartProductDto cartProductDto) {
        Product product = productService.findProductById(cartProductDto.getProductId());
        Cart cart = cartService.findCartById(cartProductDto.getCartId());

        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getProduct().equals(product)) {
                int newQuantity = cartProduct.getQuantity() + cartProductDto.getQuantity();
                if (newQuantity < 0)
                    newQuantity = 0;
                UpdateCartProductDto updateCartProductDto = new UpdateCartProductDto();
                updateCartProductDto.setCartId(cartProductDto.getCartId());
                updateCartProductDto.setCartProductId(cartProduct.getId());
                updateCartProductDto.setQuantity(newQuantity);
                return updateCartProduct(updateCartProductDto);
            }
        }

        CartProduct newCartProduct = new CartProduct();
        newCartProduct.setCart(cart);
        newCartProduct.setProduct(product);
        newCartProduct.setQuantity(cartProductDto.getQuantity());
        CartProduct cartProduct = cartProductRepository.save(newCartProduct);
        cart.getCartProducts().add(cartProduct);

        return cart;
    }

    @Override
    public Cart updateCartProduct(UpdateCartProductDto cartProductDto) {
        CartProduct cartProduct = findCartProductById(cartProductDto.getCartProductId());
        Cart cart = cartService.findCartById(cartProductDto.getCartId());
        cartProduct.setQuantity(cartProductDto.getQuantity());
        cartProductRepository.save(cartProduct);
        return cart;
    }

    @Override
    public Cart deleteCartProduct(DeleteCartProductDto cartProductDto) {
        Cart cart = cartService.findCartById(cartProductDto.getCartId());
        CartProduct cartProduct = findCartProductById(cartProductDto.getId());
        cart.getCartProducts().remove(cartProduct);
        cartProductRepository.delete(cartProduct);
        return cart;
    }

}
