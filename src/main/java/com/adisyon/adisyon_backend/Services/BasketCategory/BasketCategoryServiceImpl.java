package com.adisyon.adisyon_backend.Services.BasketCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Basket.UpdateBasketDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.CreateBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.DeleteBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.UpdateBasketCategoryDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.BasketCategory;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Repositories.BasketCategory.BasketCategoryRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;

@Service
public class BasketCategoryServiceImpl implements BasketCategoryService {

    @Autowired
    private BasketCategoryRepository basketCategoryRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BasketService basketService;

    @Override
    public BasketCategory findBasketCategoryById(Long id) {
        return Unwrapper.unwrap(basketCategoryRepository.findById(id), id);
    }

    @Override
    public List<BasketCategory> findByCompanyId(Long id) {
        return basketCategoryRepository.findByCompanyId(id);
    }

    @Override
    @Transactional
    public BasketCategory createBasketCategory(CreateBasketCategoryDto categoryDto) {
        BasketCategory newBasketCategory = new BasketCategory();
        Company company = companyService.findCompanyById(categoryDto.getCompanyId());

        newBasketCategory.setName(categoryDto.getName());
        newBasketCategory.setIsActive(true);
        newBasketCategory.setCreatedDate(LocalDateTime.now());
        newBasketCategory.setCompany(company);
        BasketCategory savedBasketCategory = basketCategoryRepository.save(newBasketCategory);

        for (Basket basket : categoryDto.getBaskets()) {
            UpdateBasketDto dto = new UpdateBasketDto();
            dto.setId(basket.getId());
            dto.setBasketCategory(savedBasketCategory);
            basketService.updateBasket(dto);
        }

        return savedBasketCategory;
    }

    @Override
    @Transactional
    public BasketCategory updateBasketCategory(UpdateBasketCategoryDto categoryDto) {
        BasketCategory basketCategory = findBasketCategoryById(categoryDto.getId());
        basketCategory
                .setName(categoryDto.getName() != null ? categoryDto.getName() : basketCategory.getName());

        for (Basket addedBasket : categoryDto.getAddedBaskets()) {
            Basket basket = basketService.findBasketById(addedBasket.getId());
            UpdateBasketDto dto = new UpdateBasketDto();
            dto.setId(basket.getId());
            dto.setBasketCategory(basketCategory);
            basketService.setBasketCategory(dto);
        }
        for (Basket removedBasket : categoryDto.getRemovedBaskets()) {
            Basket basket = basketService.findBasketById(removedBasket.getId());
            if (basket.getBasketCategory() != basketCategory)
                continue;
            basketCategory.getBaskets().remove(basket);
            UpdateBasketDto dto = new UpdateBasketDto();
            dto.setId(basket.getId());
            dto.setBasketCategory(null);
            basketService.setBasketCategory(dto);
        }

        return basketCategoryRepository.save(basketCategory);
    }

    @Override
    @Transactional
    public void deleteBasketCategory(DeleteBasketCategoryDto basketDto) {
        BasketCategory basketCategory = findBasketCategoryById(basketDto.getId());
        List<Basket> baskets = new ArrayList<>(basketCategory.getBaskets());

        for (Basket removedBasket : baskets) {
            Basket basket = basketService.findBasketById(removedBasket.getId());
            if (basket.getBasketCategory() != basketCategory)
                continue;
            basketCategory.getBaskets().remove(basket);
            UpdateBasketDto dto = new UpdateBasketDto();
            dto.setId(basket.getId());
            dto.setBasketCategory(null);
            basketService.setBasketCategory(dto);
        }

        basketCategoryRepository.delete(basketCategory);
    }

    @Override
    public void disableBasketCategory(Long id) {
        BasketCategory basketCategory = findBasketCategoryById(id);
        basketCategory.setIsActive(false);
        basketCategory.setUpdatedDate(LocalDateTime.now());
        basketCategoryRepository.save(basketCategory);
    }
}
