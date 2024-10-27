package com.adisyon.adisyon_backend.Services.Basket;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Basket.CreateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.DeleteBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.BasketCategory;

public interface BasketService {

    public Basket createBasket(CreateBasketDto basketDto, BasketCategory basketCategory);

    public Basket updateBasket(UpdateBasketDto basketDto);

    public void deleteBasket(DeleteBasketDto basketDto);

    public Basket setBasketCategory(UpdateBasketDto basketDto);

    public Basket activateBasket(Long id);

    public Basket deactivateBasket(Long id);

    public Basket findBasketById(Long id);

    public List<Basket> findByCompanyId(Long id);

    // public Basket updateTotalPrice(Long id);

}
