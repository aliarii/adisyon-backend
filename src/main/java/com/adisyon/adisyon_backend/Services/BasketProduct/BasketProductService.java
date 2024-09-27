package com.adisyon.adisyon_backend.Services.BasketProduct;

import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.CreateBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.DeleteBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.UpdateBasketProductDto;
import com.adisyon.adisyon_backend.Entities.BasketProduct;

public interface BasketProductService {
    public BasketProduct createBasketProduct(CreateBasketProductDto bProductDto);

    public BasketProduct updateBasketProduct(UpdateBasketProductDto bProductDto);

    public void deleteBasketProduct(DeleteBasketProductDto bProductDto);

    public BasketProduct findBasketProductById(Long id);
}
