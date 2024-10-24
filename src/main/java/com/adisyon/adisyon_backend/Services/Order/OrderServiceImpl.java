package com.adisyon.adisyon_backend.Services.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PayAllOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PaySelectedOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PayWithoutOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.TransferOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.CreateOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.DeleteOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.UpdateOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.Payment.CreatePaymentDto;
import com.adisyon.adisyon_backend.Dto.Request.Receipt.CreateReceiptDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.ORDER_STATUS;
import com.adisyon.adisyon_backend.Entities.Order;
import com.adisyon.adisyon_backend.Entities.OrderItem;
import com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE;
import com.adisyon.adisyon_backend.Entities.Payment;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Repositories.Order.OrderRepository;
import com.adisyon.adisyon_backend.Repositories.OrderItem.OrderItemRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Basket.BasketService;
import com.adisyon.adisyon_backend.Services.Cart.CartService;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;
import com.adisyon.adisyon_backend.Services.OrderItem.OrderItemService;
import com.adisyon.adisyon_backend.Services.Payment.PaymentService;
import com.adisyon.adisyon_backend.Services.Receipt.ReceiptService;

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

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReceiptService receiptService;

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
        newOrder.setCreatedDate(LocalDateTime.now());
        newOrder.setCompany(company);
        newOrder.setOrderItems(createOrderItems(activeCartProducts));

        // Long totalPrice = calculateTotalPrice(newOrder.getOrderItems());
        Long totalPrice = calculateTotalPrice(newOrder);
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

        order.setUpdatedDate(LocalDateTime.now());
        // order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        order.setTotalPrice(calculateTotalPrice(order));
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(DeleteOrderDto orderDto) {
        Order currentOrder = findOrderById(orderDto.getId());
        Basket basket = currentOrder.getBasket();

        List<Order> allOrders = new ArrayList<>();
        allOrders.add(currentOrder);
        allOrders.addAll(basket.getCompletedOrders());
        basket.getCompletedOrders().clear();

        for (Order order : allOrders) {
            if (order.getBasket() != null) {
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
    }

    @Override
    @Transactional
    public Order payAllOrders(PayAllOrdersDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        LocalDateTime completedDate = LocalDateTime.now();
        Basket basket = order.getBasket();

        order.getOrderItems().forEach(orderItem -> {
            completeOrderItem(orderItem, completedDate);
        });

        completeOrder(order, completedDate, orderDto.getPaymentType());
        createReceipt(basket);
        clearBasket(basket, completedDate);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order paySelectedOrders(PaySelectedOrdersDto orderDto) {
        Order order = findOrderById(orderDto.getId());

        List<OrderItem> untouchedOrderItems = new ArrayList<>();
        List<OrderItem> completedOrderItems = new ArrayList<>();
        List<OrderItem> createdOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderDto.getOrderItems()) {
            if (orderItem.getQuantity() == 0) {
                untouchedOrderItems.add(orderItemService.findOrderItemById(orderItem.getId()));
                continue;
            }
            OrderItem existingItem = orderItemService.findOrderItemById(orderItem.getId());
            if (existingItem.getQuantity() == orderItem.getQuantity()) {
                completedOrderItems.add(existingItem);
            } else {
                OrderItem cOrderItem = createOrderItem(orderItem.getProduct(), orderItem.getQuantity());
                createdOrderItems.add(cOrderItem);
                updateOrderItem(orderItem, existingItem.getQuantity() - orderItem.getQuantity());
            }
        }
        if (createdOrderItems.isEmpty() && untouchedOrderItems.isEmpty()) {
            PayAllOrdersDto dto = new PayAllOrdersDto(orderDto.getId(), orderDto.getPaymentType());
            return payAllOrders(dto);
        }

        order.getOrderItems().removeAll(completedOrderItems);

        Order newOrder = new Order();
        LocalDateTime completedDate = LocalDateTime.now();
        Basket basket = order.getBasket();

        completedOrderItems.addAll(createdOrderItems);
        newOrder.getOrderItems().addAll(completedOrderItems);
        for (OrderItem orderItem : completedOrderItems) {
            completeOrderItem(orderItem, completedDate);
        }
        newOrder.setCompany(basket.getCompany());
        newOrder.setCreatedDate(order.getCreatedDate());
        newOrder.setUpdatedDate(completedDate);
        newOrder.setCompletedDate(completedDate);
        Long totalPrice = calculateTotalPrice(newOrder.getOrderItems());
        newOrder.setTotalPrice(totalPrice);
        newOrder.getPayments().add(createPayment(totalPrice, orderDto.getPaymentType(), completedDate));

        basket.getCompletedOrders().add(newOrder);
        orderRepository.save(newOrder);

        order.setTotalPrice(calculateTotalPrice(order));
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order payWithoutOrders(PayWithoutOrdersDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        Payment payment = createPayment(orderDto.getPayAmount(), orderDto.getPaymentType(), LocalDateTime.now());
        order.getPayments().add(payment);
        order.setTotalPrice(calculateTotalPrice(order));

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order transferOrders(TransferOrdersDto orderDto) {
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
            // currentOrder.setTotalPrice(calculateTotalPrice(currentOrder.getOrderItems()));
            currentOrder.setTotalPrice(calculateTotalPrice(currentOrder));
            orderRepository.save(currentOrder);
            currentOrder.getOrderItems().addAll(completedOrders);
        }

        newOrder.setOrderItems(itemsToTransfer);
        newOrder.setBasket(newBasket);
        newOrder.setCreatedDate(LocalDateTime.now());
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
                    return createOrderItem(cartProduct.getProduct(), cartProduct.getQuantity());
                })
                .collect(Collectors.toList());
    }

    private OrderItem createOrderItem(Product product, int quantity) {
        CreateOrderItemDto dto = new CreateOrderItemDto();
        dto.setProductId(product.getId());
        dto.setQuantity(quantity);
        return orderItemService.createOrderItem(dto);
    }

    private void updateOrderItems(Order order, List<CartProduct> cartProducts) {
        for (CartProduct cartProduct : cartProducts) {
            order.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getProduct().equals(cartProduct.getProduct())
                            && orderItem.getStatus() != ORDER_STATUS.STATUS_COMPLETED)
                    .findFirst()
                    .ifPresentOrElse(orderItem -> {
                        updateOrderItem(orderItem, orderItem.getQuantity() + cartProduct.getQuantity());
                    }, () -> {
                        order.getOrderItems().add(createOrderItem(cartProduct.getProduct(), cartProduct.getQuantity()));
                    });
        }
    }

    private OrderItem updateOrderItem(OrderItem orderItem, int quantity) {
        UpdateOrderItemDto dto = new UpdateOrderItemDto();
        dto.setId(orderItem.getId());
        dto.setQuantity(quantity);
        return orderItemService.updateOrderItem(dto);
    }

    private void completeOrderItem(OrderItem orderItem, LocalDateTime completedDate) {
        orderItem.setCompletedDate(completedDate);
        orderItem.setUpdatedDate(completedDate);
        orderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
        orderItemRepository.save(orderItem);
    }

    private void completeOrder(Order order, LocalDateTime completedDate, String paymentType) {
        order.setUpdatedDate(completedDate);
        order.setCompletedDate(completedDate);
        order.setBasket(null);
        // Long totalPrice = calculateTotalPrice(order.getOrderItems());
        Long totalPrice = calculateTotalPrice(order);
        order.setTotalPrice(totalPrice);
        order.getPayments().add(createPayment(totalPrice, paymentType, completedDate));
    }

    private void clearBasket(Basket basket, LocalDateTime completedDate) {
        basket.getCart().getCartProducts().clear();
        basket.setIsActive(false);
        basket.setUpdatedDate(completedDate);
        basket.setOrder(null);
        basket.getCompletedOrders().clear();
    }

    public Payment createPayment(Long payAmount, String paymentType, LocalDateTime completedDate) {
        CreatePaymentDto createDto = new CreatePaymentDto();
        createDto.setPayAmount(payAmount);
        createDto.setPaymentType(findPaymentType(paymentType));
        Payment payment = paymentService.createPayment(createDto);
        payment.setCompletedDate(completedDate);
        return payment;
    }

    public void createReceipt(Basket basket) {
        CreateReceiptDto receiptDto = new CreateReceiptDto();
        receiptDto.setBasket(basket);
        receiptDto.setCompanyId(basket.getCompany().getId());
        receiptDto.getOrders().addAll(basket.getCompletedOrders());
        receiptDto.getOrders().add(basket.getOrder());
        receiptService.createReceipt(receiptDto);
    }

    private List<OrderItem> transferOrderItems(TransferOrdersDto orderDto, Order currentOrder) {
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
        return orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum();
    }

    private Long calculateTotalPrice(Order order) {
        Long totalPrice = order.getOrderItems().stream().mapToLong(OrderItem::getTotalPrice).sum();
        totalPrice -= order.getPayments().stream().mapToLong(Payment::getPayAmount).sum();
        return totalPrice;
    }

    public PAYMENT_TYPE findPaymentType(String type) {
        PAYMENT_TYPE pType = null;
        switch (type) {
            case "Cash":
                pType = PAYMENT_TYPE.TYPE_CASH;
                break;
            case "CreditCard":
                pType = PAYMENT_TYPE.TYPE_CREDIT_CARD;
                break;
        }

        return pType;
    }
}
