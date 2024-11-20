package com.adisyon.adisyon_backend.Services.Basket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.DeleteBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Cart.CreateCartDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.BasketCategory;
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
    @Transactional
    public Basket createBasket(CreateBasketDto basketDto, BasketCategory basketCategory) {

        Basket newBasket = new Basket();
        Integer count = findByCompanyId(basketDto.getCompany().getId()).size();
        if (basketDto.getName() != null && basketDto.getName() != "") {
            newBasket.setCustomName(basketDto.getName());
            newBasket.setName("Basket " + (count + 1));

        } else {
            newBasket.setName("Basket " + (count + 1));
        }
        newBasket.setCompany(basketDto.getCompany());
        newBasket.setIsActive(false);
        newBasket.setCreatedDate(LocalDateTime.now());
        newBasket.setBasketCategory(basketCategory);
        basketRepository.save(newBasket);

        CreateCartDto cartDto = new CreateCartDto();
        cartDto.setBasket(newBasket);
        cartService.createCart(cartDto);

        return newBasket;
    }

    @Override
    @Transactional
    public Basket updateBasket(UpdateBasketDto basketDto) {
        Basket basket = findBasketById(basketDto.getId());
        if (basket.getName() == null) {
            Integer count = findByCompanyId(basket.getCompany().getId()).size();
            basket.setName("Basket " + (count));
        }

        basket.setCustomName(basketDto.getName() != null ? basketDto.getName() : basket.getCustomName());
        basket.setBasketCategory(
                basketDto.getBasketCategory() != null ? basketDto.getBasketCategory() : basket.getBasketCategory());
        basket.setUpdatedDate(LocalDateTime.now());

        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public void deleteBasket(DeleteBasketDto basketDto) {
        Basket basket = findBasketById(basketDto.getId());
        basketRepository.delete(basket);
    }

    @Override
    @Transactional
    public Basket setBasketCategory(UpdateBasketDto basketDto) {
        Basket basket = findBasketById(basketDto.getId());
        basket.setBasketCategory(basketDto.getBasketCategory());
        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public Basket activateBasket(Long id) {
        Basket basket = findBasketById(id);
        basket.setUpdatedDate(LocalDateTime.now());
        basket.setActivatedDate(LocalDateTime.now());
        basket.setIsActive(true);
        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public Basket deactivateBasket(Long id) {
        Basket basket = findBasketById(id);
        basket.setIsActive(false);
        basket.setUpdatedDate(LocalDateTime.now());
        basket.setDeactivatedDate(LocalDateTime.now());
        return basketRepository.save(basket);
    }
    // @Override
    // public Basket updateTotalPrice(Long id){
    // Basket basket = findBasketById(id);
    // orderse basket.getOrder()
    // }

}
