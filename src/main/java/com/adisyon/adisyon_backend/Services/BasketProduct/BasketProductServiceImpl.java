package com.adisyon.adisyon_backend.Services.BasketProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.CreateBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.DeleteBasketProductDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketProduct.UpdateBasketProductDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.BasketProduct;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Repositories.BasketProduct.BasketProductRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@Service
public class BasketProductServiceImpl implements BasketProductService {

    @Autowired
    private BasketProductRepository basketProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    // @Autowired
    // private BasketRepository basketRepository;

    @Override
    public BasketProduct findBasketProductById(Long id) {
        return Unwrapper.unwrap(basketProductRepository.findById(id), id);
    }

    @Override
    public BasketProduct createBasketProduct(CreateBasketProductDto bProductDto) {

        Product product = productService.findProductById(bProductDto.getProductId());
        Basket basket = basketService.findBasketById(bProductDto.getBasketId());
        for (BasketProduct basketProduct : basket.getBasketProducts()) {
            if (basketProduct.getProduct().equals(product)) {
                int newQuantity = basketProduct.getQuantity() + bProductDto.getQuantity();
                UpdateBasketProductDto newBPDto = new UpdateBasketProductDto(basketProduct.getId(), newQuantity);
                return updateBasketProduct(newBPDto);
            }
        }

        BasketProduct newBasketProduct = new BasketProduct();
        newBasketProduct.setProduct(product);
        newBasketProduct.setBasket(basket);
        newBasketProduct.setQuantity(bProductDto.getQuantity());
        newBasketProduct.setTotalPrice(bProductDto.getQuantity() * product.getPrice());

        BasketProduct savedBasketProduct = basketProductRepository.save(newBasketProduct);
        basket.getBasketProducts().add(savedBasketProduct);
        return savedBasketProduct;
    }

    @Override
    public BasketProduct updateBasketProduct(UpdateBasketProductDto bProductDto) {
        BasketProduct basketProduct = findBasketProductById(bProductDto.getId());
        basketProduct.setQuantity(bProductDto.getQuantity());
        basketProduct.setTotalPrice(basketProduct.getProduct().getPrice() * bProductDto.getQuantity());
        return basketProductRepository.save(basketProduct);
    }

    @Override
    // maybe return basket?
    public void deleteBasketProduct(DeleteBasketProductDto bProductDto) {
        Basket basket = basketService.findBasketById(bProductDto.getId());
        BasketProduct basketProduct = findBasketProductById(bProductDto.getId());
        basket.getBasketProducts().remove(basketProduct);

        // return basketRepository.save(basket);
    }

}
