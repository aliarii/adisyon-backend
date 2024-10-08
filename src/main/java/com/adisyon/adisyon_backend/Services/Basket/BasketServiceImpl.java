package com.adisyon.adisyon_backend.Services.Basket;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.DeleteBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Repositories.Basket.BasketRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public Basket findBasketById(Long id) {
        return Unwrapper.unwrap(basketRepository.findById(id), id);
    }

    @Override
    public Basket createBasket(CreateBasketDto basketDto) {

        Basket newBasket = new Basket();
        newBasket.setName(basketDto.getName());
        newBasket.setCompany(basketDto.getCompany());
        newBasket.setIsActive(false);
        newBasket.setCreatedDate(new Date());
        newBasket.setBasketCategory(basketDto.getBasketCategory());

        return basketRepository.save(newBasket);
    }

    @Override
    public Basket updateBasket(UpdateBasketDto basketDto) {

        Basket basket = findBasketById(basketDto.getId());
        basket.setName(basketDto.getName() != null ? basketDto.getName()
                : basket.getName());
        basket.getBasketProducts().addAll(basketDto.getBasketProducts());
        basket.setBasketCategory(
                basketDto.getBasketCategory() != null ? basketDto.getBasketCategory() : basket.getBasketCategory());
        basket.setUpdatedDate(new Date());
        basket.setIsActive(true);
        return basketRepository.save(basket);
    }

    @Override
    public void deleteBasket(DeleteBasketDto basketDto) {
        Basket basket = findBasketById(basketDto.getId());
        basketRepository.delete(basket);
    }

    @Override
    public void disableBasket(Long id) {
        Basket basket = findBasketById(id);
        basket.setIsActive(false);
        basket.setUpdatedDate(new Date());
        basketRepository.save(basket);
    }

    @Override
    public List<Basket> findByCompanyId(Long id) {
        return basketRepository.findByCompanyId(id);
    }

}
