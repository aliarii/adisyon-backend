package com.adisyon.adisyon_backend.Services.BasketCategory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.CreateBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.DeleteBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.UpdateBasketCategoryDto;
import com.adisyon.adisyon_backend.Entities.BasketCategory;
import com.adisyon.adisyon_backend.Repositories.BasketCategory.BasketCategoryRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class BasketCategoryServiceImpl implements BasketCategoryService {

    @Autowired
    private BasketCategoryRepository basketCategoryRepository;

    @Override
    public BasketCategory findBasketCategoryById(Long id) {
        return Unwrapper.unwrap(basketCategoryRepository.findById(id), id);
    }

    @Override
    public BasketCategory createBasketCategory(CreateBasketCategoryDto categoryDto) {
        BasketCategory newBasketCategory = new BasketCategory();
        newBasketCategory.setName(categoryDto.getName());
        newBasketCategory.setIsActive(true);
        newBasketCategory.setCreatedDate(new Date());

        return basketCategoryRepository.save(newBasketCategory);
    }

    @Override
    public BasketCategory updateBasketCategory(UpdateBasketCategoryDto basketDto) {
        BasketCategory basketCategory = findBasketCategoryById(basketDto.getId());
        basketCategory.setName(basketDto.getName() != null ? basketDto.getName() : basketCategory.getName());
        basketCategory.setUpdatedDate(new Date());

        return basketCategoryRepository.save(basketCategory);
    }

    @Override
    public void deleteBasketCategory(DeleteBasketCategoryDto basketDto) {
        BasketCategory basketCategory = findBasketCategoryById(basketDto.getId());
        basketCategoryRepository.delete(basketCategory);
    }

    @Override
    public void disableBasketCategory(Long id) {
        BasketCategory basketCategory = findBasketCategoryById(id);
        basketCategory.setIsActive(false);
        basketCategory.setUpdatedDate(new Date());
        basketCategoryRepository.save(basketCategory);
    }

    @Override
    public List<BasketCategory> findByCompanyId(Long id) {
        return basketCategoryRepository.findByCompanyId(id);
    }

}