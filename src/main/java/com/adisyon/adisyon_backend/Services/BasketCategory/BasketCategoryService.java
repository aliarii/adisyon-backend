package com.adisyon.adisyon_backend.Services.BasketCategory;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.CreateBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.DeleteBasketCategoryDto;
import com.adisyon.adisyon_backend.Dto.Request.BasketCategory.UpdateBasketCategoryDto;
import com.adisyon.adisyon_backend.Entities.BasketCategory;

public interface BasketCategoryService {
    public BasketCategory createBasketCategory(CreateBasketCategoryDto basketDto);

    public BasketCategory updateBasketCategory(UpdateBasketCategoryDto basketDto);

    public void deleteBasketCategory(DeleteBasketCategoryDto basketDto);

    public void disableBasketCategory(Long id);

    public BasketCategory findBasketCategoryById(Long id);

    public List<BasketCategory> findByCompanyId(Long id);
}
