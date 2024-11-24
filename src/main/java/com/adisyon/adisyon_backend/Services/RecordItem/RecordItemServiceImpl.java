package com.adisyon.adisyon_backend.Services.RecordItem;

import java.time.LocalDateTime;
import java.time.YearMonth;
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
import com.adisyon.adisyon_backend.Entities.OrderItem;
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
        switch (recordDto.getRecordItemType()) {
            case ORDER_ADD:
                return orderAdd(recordDto, jwt);

            case ORDER_REMOVE:
                return null;

            case ORDER_UPDATE:
                return null;

            case ORDER_CANCEL:
                return null;

            case ORDER_COMPLETE:
                return null;

            case ORDER_TRANSFER_ALL:
                return orderTransferAll(recordDto, jwt);

            case ORDER_TRANSFER_SELECTIVELY:
                return null;

            case ORDER_PAY_ALL:
                return null;

            case ORDER_PAY_SELECTIVELY:
                return null;

            case ORDER_PAY_AMOUNT:
                return orderPayAmount(recordDto, jwt);

            default:
                return null;
        }

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

    @Override
    @Transactional
    public List<RecordItem> findRecordItemsByMonth(Long id, Integer year, Integer month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return recordItemRepository.findRecordItemsByMonth(id, startOfMonth, endOfMonth);
    }

    private RecordItem orderAdd(CreateRecordItemDto recordDto, String jwt) {
        Basket basket = basketService.findBasketById(recordDto.getCurrentBasketId());
        Cart cart = cartService.findCartByBasketId(basket.getId());
        Company company = companyService.findCompanyById(basket.getCompany().getId());
        User user = userService.findUserByJwtToken(jwt);

        List<CartProduct> cartProducts = filterCartProducts(cart);
        if (cartProducts.isEmpty()) {
            throw new IllegalArgumentException("No valid products in cart.");
        }

        RecordItem recordItem = new RecordItem();
        recordItem.setCurrentBasketName(
                (basket.getCustomName() == null || basket.getCustomName() == "") ? basket.getName()
                        : basket.getCustomName());
        recordItem.setCompany(company);
        recordItem.setUserName(user.getUserName());
        recordItem.setCreatedDate(LocalDateTime.now());
        for (CartProduct cartProduct : cartProducts) {
            recordItem.getRecordItemProducts()
                    .add(createRecordItemProduct(cartProduct.getProduct().getName(), cartProduct.getQuantity()));
        }
        recordItem.setRecordItemType(recordDto.getRecordItemType());
        return recordItemRepository.save(recordItem);
    }

    private RecordItem orderPayAmount(CreateRecordItemDto recordDto, String jwt) {
        Basket basket = basketService.findBasketById(recordDto.getCurrentBasketId());
        Company company = companyService.findCompanyById(basket.getCompany().getId());
        User user = userService.findUserByJwtToken(jwt);

        RecordItem recordItem = new RecordItem();
        recordItem.setCurrentBasketName(
                (basket.getCustomName() == null || basket.getCustomName() == "") ? basket.getName()
                        : basket.getCustomName());
        recordItem.setCompany(company);
        recordItem.setUserName(user.getUserName());
        recordItem.setCreatedDate(LocalDateTime.now());
        recordItem.setRecordItemType(recordDto.getRecordItemType());
        recordItem.setPayAmount(recordDto.getPayAmount());
        recordItem.setPaymentType(recordDto.getPaymentType());

        return recordItemRepository.save(recordItem);
    }

    private RecordItem orderTransferAll(CreateRecordItemDto recordDto, String jwt) {
        Basket currentBasket = basketService.findBasketById(recordDto.getCurrentBasketId());
        Basket targetBasket = basketService.findBasketById(recordDto.getTargetBasketId());
        Company company = companyService.findCompanyById(currentBasket.getCompany().getId());
        User user = userService.findUserByJwtToken(jwt);

        RecordItem recordItem = new RecordItem();
        recordItem.setCurrentBasketName(
                (currentBasket.getCustomName() == null || currentBasket.getCustomName() == "") ? currentBasket.getName()
                        : currentBasket.getCustomName());
        recordItem.setTargetBasketName(
                (targetBasket.getCustomName() == null || targetBasket.getCustomName() == "") ? targetBasket.getName()
                        : targetBasket.getCustomName());
        recordItem.setCompany(company);
        recordItem.setUserName(user.getUserName());
        recordItem.setCreatedDate(LocalDateTime.now());
        recordItem.setRecordItemType(recordDto.getRecordItemType());

        for (OrderItem orderItem : recordDto.getOrderItems()) {
            recordItem.getRecordItemProducts()
                    .add(createRecordItemProduct(orderItem.getProduct().getName(), orderItem.getQuantity()));
        }

        return recordItemRepository.save(recordItem);
    }

    private List<CartProduct> filterCartProducts(Cart cart) {
        return cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    private RecordItemProduct createRecordItemProduct(String name, Integer quantity) {
        // return new RecordItemProduct(product.getProduct().getName(),
        // product.getQuantity());
        return new RecordItemProduct(name, quantity);
    }

}
