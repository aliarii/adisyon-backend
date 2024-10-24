package com.adisyon.adisyon_backend.Services.Basket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.DeleteBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.CreateCartDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Repositories.Basket.BasketRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Cart.CartService;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Basket findBasketById(Long id) {
        return Unwrapper.unwrap(basketRepository.findById(id), id);
    }

    @Override
    public List<Basket> findByCompanyId(Long id) {
        return basketRepository.findByCompanyId(id);
    }

    @Override
    public Basket createBasket(CreateBasketDto basketDto) {

        Basket newBasket = new Basket();
        newBasket.setName(basketDto.getName());
        newBasket.setCompany(basketDto.getCompany());
        newBasket.setIsActive(false);
        newBasket.setCreatedDate(LocalDateTime.now());
        newBasket.setBasketCategory(basketDto.getBasketCategory());
        basketRepository.save(newBasket);

        CreateCartDto cartDto = new CreateCartDto();
        cartDto.setBasket(newBasket);
        cartService.createCart(cartDto);

        return newBasket;
    }

    @Override
    public Basket updateBasket(UpdateBasketDto basketDto) {

        Basket basket = findBasketById(basketDto.getId());
        basket.setName(basketDto.getName() != null ? basketDto.getName()
                : basket.getName());
        basket.setBasketCategory(
                basketDto.getBasketCategory() != null ? basketDto.getBasketCategory() : basket.getBasketCategory());
        basket.setUpdatedDate(LocalDateTime.now());

        return basketRepository.save(basket);
    }

    @Override
    public void deleteBasket(DeleteBasketDto basketDto) {
        Basket basket = findBasketById(basketDto.getId());
        basketRepository.delete(basket);
    }

    @Override
    public Basket activateBasket(Long id) {
        Basket basket = findBasketById(id);
        basket.setUpdatedDate(LocalDateTime.now());
        basket.setActivatedDate(LocalDateTime.now());
        basket.setIsActive(true);
        return basketRepository.save(basket);
    }

    @Override
    public Basket deactivateBasket(Long id) {
        Basket basket = findBasketById(id);
        basket.setIsActive(false);
        basket.setUpdatedDate(LocalDateTime.now());
        basket.setDeactivatedDate(LocalDateTime.now());
        return basketRepository.save(basket);
    }
}
