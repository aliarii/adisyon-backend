package com.adisyon.adisyon_backend.Services.RecordItem;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
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
import com.adisyon.adisyon_backend.Entities.RECORD_ITEM_TYPE;
import com.adisyon.adisyon_backend.Entities.RecordItem;
import com.adisyon.adisyon_backend.Entities.RecordItemProduct;
import com.adisyon.adisyon_backend.Entities.User;
import com.adisyon.adisyon_backend.Repositories.RecordItem.RecordItemRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.OrderItem.OrderItemService;
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

    @Autowired
    private OrderItemService orderItemService;

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
                return createOrderAdd(recordDto, jwt);

            case ORDER_REMOVE:
                return null;

            case ORDER_UPDATE:
                return createOrderUpdate(recordDto, jwt);

            case ORDER_CANCEL:
                return null;

            case ORDER_COMPLETE:
                return null;

            case ORDER_TRANSFER_ALL:
                return createOrderTransferAll(recordDto, jwt);

            case ORDER_TRANSFER_SELECTIVELY:
                return null;

            case ORDER_PAY_ALL:
                return null;

            case ORDER_PAY_SELECTIVELY:
                return null;

            case ORDER_PAY_AMOUNT:
                return createOrderPayAmount(recordDto, jwt);

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
    public List<RecordItem> findRecordItemsByDay(Long id, Integer year, Integer month, Integer day) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(day).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atDay(day).atTime(23, 59, 59);

        return recordItemRepository.findRecordItemsByMonth(id, startOfMonth, endOfMonth);
    }

    @Override
    @Transactional
    public List<RecordItem> findRecordItemsByMonth(Long id, Integer year, Integer month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return recordItemRepository.findRecordItemsByMonth(id, startOfMonth, endOfMonth);
    }

    private RecordItem createOrderAdd(CreateRecordItemDto recordDto, String jwt) {
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
                    .add(createRecordItemProduct(cartProduct.getProduct().getName(), cartProduct.getQuantity(),
                            cartProduct.getQuantity()));
        }
        recordItem.setRecordItemType(recordDto.getRecordItemType());
        return recordItemRepository.save(recordItem);
    }

    private RecordItem createOrderRemove(CreateRecordItemDto recordDto, String jwt) {
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

        for (OrderItem orderItem : recordDto.getOrderItems()) {
            OrderItem existingOrderItem = orderItemService.findOrderItemById(orderItem.getId());
            recordItem.getRecordItemProducts()
                    .add(createRecordItemProduct(orderItem.getProduct().getName(), orderItem.getQuantity(),
                            existingOrderItem.getQuantity()));
        }

        return recordItemRepository.save(recordItem);
    }

    private RecordItem createOrderUpdate(CreateRecordItemDto recordDto, String jwt) {
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

        List<OrderItem> removedItems = new ArrayList<>();
        List<OrderItem> changedItems = new ArrayList<>();

        for (OrderItem orderItem : recordDto.getOrderItems()) {
            OrderItem existingOrderItem = orderItemService.findOrderItemById(orderItem.getId());
            if (existingOrderItem.getQuantity() == orderItem.getQuantity())
                continue;
            if (orderItem.getQuantity() == 0) {
                removedItems.add(orderItem);
                continue;
            }
            changedItems.add(orderItem);
            recordItem.getRecordItemProducts()
                    .add(createRecordItemProduct(orderItem.getProduct().getName(), orderItem.getQuantity(),
                            existingOrderItem.getQuantity()));
        }
        if (removedItems.size() > 0) {
            CreateRecordItemDto newDto = new CreateRecordItemDto();
            newDto.setCurrentBasketId(recordDto.getCurrentBasketId());
            newDto.setOrderItems(removedItems);
            newDto.setRecordItemType(RECORD_ITEM_TYPE.ORDER_REMOVE);
            createOrderRemove(newDto, jwt);
        }
        if (changedItems.size() > 0)
            return recordItemRepository.save(recordItem);
        else
            return null;
    }

    private RecordItem createOrderPayAmount(CreateRecordItemDto recordDto, String jwt) {
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

    private RecordItem createOrderTransferAll(CreateRecordItemDto recordDto, String jwt) {
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
                    .add(createRecordItemProduct(orderItem.getProduct().getName(), orderItem.getQuantity(),
                            orderItem.getQuantity()));
        }

        return recordItemRepository.save(recordItem);
    }

    private List<CartProduct> filterCartProducts(Cart cart) {
        return cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    private RecordItemProduct createRecordItemProduct(String name, Integer curQuantity, Integer oldQuantity) {
        return new RecordItemProduct(name, curQuantity, oldQuantity);
    }

}
