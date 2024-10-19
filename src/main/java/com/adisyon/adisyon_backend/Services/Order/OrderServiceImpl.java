package com.adisyon.adisyon_backend.Services.Order;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Order.CompleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CompleteOrderItemsDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PartialPayOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.TransferOrderItemsDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.CreateOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.DeleteOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.UpdateOrderItemDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.ORDER_STATUS;
import com.adisyon.adisyon_backend.Entities.Order;
import com.adisyon.adisyon_backend.Entities.OrderItem;
import com.adisyon.adisyon_backend.Repositories.Order.OrderRepository;
import com.adisyon.adisyon_backend.Repositories.OrderItem.OrderItemRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.OrderItem.OrderItemService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order findOrderById(Long id) {
        return Unwrapper.unwrap(orderRepository.findById(id), id);
    }

    @Override
    public Order findOrderByBasketId(Long id) {
        return orderRepository.findByBasketId(id);
    }

    @Override
    public List<Order> findOrdersByCompanyId(Long id) {
        return orderRepository.findByCompanyId(id);
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderDto orderDto) {
        Basket basket = basketService.findBasketById(orderDto.getBasketId());
        Cart cart = cartService.findCartByBasketId(basket.getId());
        Company company = companyService.findCompanyById(basket.getCompany().getId());

        List<CartProduct> activeCartProducts = filterActiveCartProducts(cart);
        if (activeCartProducts.isEmpty()) {
            throw new IllegalArgumentException("No valid products in cart.");
        }

        Order newOrder = new Order();
        newOrder.setBasket(basket);
        newOrder.setCreatedDate(new Date());
        newOrder.setCompany(company);
        newOrder.setOrderItems(createOrderItems(activeCartProducts));

        Long totalPrice = calculateTotalPrice(newOrder.getOrderItems());
        newOrder.setTotalPrice(totalPrice);

        Order createdOrder = orderRepository.save(newOrder);
        basket.setOrder(createdOrder);

        return createdOrder;
    }

    @Override
    @Transactional
    public Order updateOrder(UpdateOrderDto orderDto) {
        Order order = findOrderById(orderDto.getOrderId());
        Cart cart = cartService.findCartByBasketId(order.getBasket().getId());

        List<CartProduct> activeCartProducts = filterActiveCartProducts(cart);
        if (activeCartProducts.isEmpty()) {
            throw new IllegalArgumentException("No valid products in cart.");
        }

        updateOrderItems(order, activeCartProducts);

        order.setUpdatedDate(new Date());
        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(DeleteOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        if (order.getBasket() != null) {
            Basket basket = order.getBasket();
            basket.setIsActive(false);
            basket.setOrder(null);
        }
        if (order.getCompany() != null) {
            order.getCompany().getOrders().remove(order);
        }

        order.getOrderItems()
                .forEach(orderItem -> orderItemService.deleteOrderItem(new DeleteOrderItemDto(orderItem.getId())));

        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public Order completeOrder(CompleteOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        Date completedDate = new Date();
        Basket basket = order.getBasket();

        order.getOrderItems().forEach(orderItem -> {
            orderItem.setCompletedDate(completedDate);
            orderItem.setUpdatedDate(completedDate);
            orderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
        });

        order.setUpdatedDate(completedDate);
        order.setCompletedDate(completedDate);
        order.setBasket(null);
        Long totalPrice = 0L;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalPrice += (orderItem.getProduct().getId() == 4 ? 0 : orderItem.getTotalPrice());
        }
        order.setTotalPrice(totalPrice);

        basket.getCart().getCartProducts().clear();
        basket.setIsActive(false);
        basket.setUpdatedDate(completedDate);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order completeOrderItems(CompleteOrderItemsDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        Date completedDate = new Date();

        for (OrderItem orderItem : orderDto.getOrderItems()) {
            if (orderItem.getQuantity() == 0)
                continue;

            OrderItem originalOrderItem = orderItemService.findOrderItemById(orderItem.getId());
            if (originalOrderItem.getQuantity() == orderItem.getQuantity()) {
                completeOrderItem(originalOrderItem, completedDate);
            } else {
                createNewOrderItem(order, orderItem, originalOrderItem, completedDate);
            }
        }

        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        if (order.getTotalPrice() == 0) {
            return completeOrder(new CompleteOrderDto(order.getId()));
        }
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order partialPayOrder(PartialPayOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());

        CreateOrderItemDto createDto = new CreateOrderItemDto();
        createDto.setProductId(4L);
        createDto.setQuantity(orderDto.getQuantity());

        OrderItem newOrderItem = orderItemService.createOrderItem(createDto);
        newOrderItem.setCreatedDate(new Date());
        newOrderItem.setCompletedDate(new Date());
        newOrderItem.setUpdatedDate(new Date());
        newOrderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
        order.getOrderItems().add(newOrderItem);

        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order transferOrderItems(TransferOrderItemsDto orderDto) {
        Order currentOrder = findOrderById(orderDto.getId());
        Basket curBasket = currentOrder.getBasket();
        Basket newBasket = basketService.findBasketById(orderDto.getBasketId());
        Order newOrder = new Order();

        List<OrderItem> completedOrders = currentOrder.getOrderItems().stream()
                .filter(orderItem -> orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED)
                .collect(Collectors.toList());
        currentOrder.getOrderItems().removeAll(completedOrders);

        List<OrderItem> itemsToTransfer = transferOrderItems(orderDto, currentOrder);

        if (currentOrder.getOrderItems().isEmpty()) {
            deleteOrder(new DeleteOrderDto(currentOrder.getId()));
            curBasket.setOrder(null);
            curBasket.setIsActive(false);
            itemsToTransfer.addAll(completedOrders);
        } else {
            currentOrder.setTotalPrice(calculateTotalPrice(currentOrder.getOrderItems()));
            orderRepository.save(currentOrder);
            currentOrder.getOrderItems().addAll(completedOrders);
        }

        newOrder.setOrderItems(itemsToTransfer);
        newOrder.setBasket(newBasket);
        newOrder.setCreatedDate(new Date());
        newOrder.setCompany(newBasket.getCompany());
        newOrder.setTotalPrice(calculateTotalPrice(itemsToTransfer));

        newBasket.setOrder(newOrder);
        newBasket.setIsActive(true);

        return orderRepository.save(newOrder);
    }

    private List<CartProduct> filterActiveCartProducts(Cart cart) {
        return cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    private List<OrderItem> createOrderItems(List<CartProduct> cartProducts) {
        return cartProducts.stream()
                .map(cartProduct -> {
                    CreateOrderItemDto dto = new CreateOrderItemDto();
                    dto.setProductId(cartProduct.getProduct().getId());
                    dto.setQuantity(cartProduct.getQuantity());
                    return orderItemService.createOrderItem(dto);
                })
                .collect(Collectors.toList());
    }

    private void updateOrderItems(Order order, List<CartProduct> cartProducts) {
        for (CartProduct cartProduct : cartProducts) {
            order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getProduct().equals(cartProduct.getProduct())
                            && orderItem.getStatus() != ORDER_STATUS.STATUS_COMPLETED)
                    .findFirst()
                    .ifPresentOrElse(orderItem -> {
                        orderItem.setQuantity(orderItem.getQuantity() + cartProduct.getQuantity());
                        orderItem.setTotalPrice(orderItem.getQuantity() * orderItem.getProduct().getPrice());
                    }, () -> {
                        CreateOrderItemDto dto = new CreateOrderItemDto();
                        dto.setProductId(cartProduct.getProduct().getId());
                        dto.setQuantity(cartProduct.getQuantity());
                        order.getOrderItems().add(orderItemService.createOrderItem(dto));
                    });
        }
    }

    private void completeOrderItem(OrderItem orderItem, Date completedDate) {
        orderItem.setCompletedDate(completedDate);
        orderItem.setUpdatedDate(completedDate);
        orderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
        orderItemRepository.save(orderItem);
    }

    private void createNewOrderItem(Order order, OrderItem orderItem, OrderItem originalOrderItem,
            Date completedDate) {
        CreateOrderItemDto dto = new CreateOrderItemDto();
        dto.setProductId(orderItem.getProduct().getId());
        dto.setQuantity(orderItem.getQuantity());

        OrderItem newOrderItem = orderItemService.createOrderItem(dto);
        newOrderItem.setCreatedDate(originalOrderItem.getCreatedDate());
        newOrderItem.setCompletedDate(completedDate);
        newOrderItem.setUpdatedDate(completedDate);
        newOrderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
        order.getOrderItems().add(newOrderItem);

        orderItemService.updateOrderItem(new UpdateOrderItemDto(orderItem.getId(),
                originalOrderItem.getQuantity() - orderItem.getQuantity()));
    }

    private List<OrderItem> transferOrderItems(TransferOrderItemsDto orderDto, Order currentOrder) {
        List<OrderItem> itemsToTransfer = orderDto.getOrderItems().stream()
                .filter(orderItem -> orderItem.getQuantity() > 0)
                .map(orderItemDto -> {
                    OrderItem originalOrderItem = orderItemService.findOrderItemById(orderItemDto.getId());
                    if (originalOrderItem.getQuantity() == orderItemDto.getQuantity()) {
                        currentOrder.getOrderItems().remove(originalOrderItem);
                        return originalOrderItem;
                    } else {
                        CreateOrderItemDto newDto = new CreateOrderItemDto();
                        newDto.setProductId(orderItemDto.getProduct().getId());
                        newDto.setQuantity(orderItemDto.getQuantity());
                        OrderItem newOrderItem = orderItemService.createOrderItem(newDto);
                        orderItemService.updateOrderItem(new UpdateOrderItemDto(orderItemDto.getId(),
                                originalOrderItem.getQuantity() - orderItemDto.getQuantity()));
                        return newOrderItem;
                    }
                })
                .collect(Collectors.toList());

        return itemsToTransfer;
    }

    private Long calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .filter(orderItem -> orderItem.getStatus() != ORDER_STATUS.STATUS_COMPLETED
                        || (orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED
                                && orderItem.getProduct().getId() == 4))
                .mapToLong(OrderItem::getTotalPrice)
                .sum();
    }
}
