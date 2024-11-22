package com.adisyon.adisyon_backend.Services.RecordItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.RecordItem.CreateRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.DeleteRecordItemDto;
import com.adisyon.adisyon_backend.Dto.Request.RecordItem.UpdateRecordItemDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.RecordItem;
import com.adisyon.adisyon_backend.Entities.RecordItemProduct;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Repositories.RecordItem.RecordItemRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.User.UserService;

@Service
public class RecordItemServiceImpl implements RecordItemService {

    @Autowired
    private RecordItemRepository recordItemRepository;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Override
    public RecordItem findRecordItemById(Long id) {
        return Unwrapper.unwrap(recordItemRepository.findById(id), id);
    }

    @Override
    public List<RecordItem> findRecordItemsByCompanyId(Long id) {
        return recordItemRepository.findByCompanyId(id);
    }

    @Override
    @Transactional
    public RecordItem createRecordItem(CreateRecordItemDto recordDto, String jwt) {
        Basket basket = basketService.findBasketById(recordDto.getBasketId());
        Cart cart = cartService.findCartByBasketId(basket.getId());
        Company company = companyService.findCompanyById(basket.getCompany().getId());
        User user = userService.findUserByJwtToken(jwt);

        List<CartProduct> cartProducts = filterCartProducts(cart);
        if (cartProducts.isEmpty()) {
            throw new IllegalArgumentException("No valid products in cart.");
        }

        RecordItem recordItem = new RecordItem();
        recordItem.setBasketName((basket.getCustomName() == null || basket.getCustomName() == "") ? basket.getName()
                : basket.getCustomName());
        recordItem.setCompany(company);
        recordItem.setUserName(user.getUserName());
        recordItem.setCreatedDate(LocalDateTime.now());
        for (CartProduct cartProduct : cartProducts) {
            recordItem.getRecordItemProducts().add(createRecordItemProduct(cartProduct));
        }

        return recordItemRepository.save(recordItem);
    }

    @Override
    public RecordItem updateRecordItem(UpdateRecordItemDto recordDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRecordItem'");
    }

    @Override
    public void deleteRecordItem(DeleteRecordItemDto recordDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecordItem'");
    }

    private List<CartProduct> filterCartProducts(Cart cart) {
        return cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    private RecordItemProduct createRecordItemProduct(CartProduct cartProduct) {
        return new RecordItemProduct(cartProduct.getProduct().getName(), cartProduct.getQuantity());
    }
}
