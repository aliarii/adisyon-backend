package com.adisyon.adisyon_backend.Services.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Order.CompleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PartialCompleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.TransferOrderDto;
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
    public Order createOrder(CreateOrderDto orderDto) {
        Basket basket = basketService.findBasketById(orderDto.getBasketId());
        Cart cart = cartService.findCartByBasketId(basket.getId());
        Company company = companyService.findCompanyById(basket.getCompany().getId());

        List<CartProduct> tempCartProducts = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getQuantity() > 0) {
                tempCartProducts.add(cartProduct);
            }
        }
        if (tempCartProducts.isEmpty())
            return null;

        Order newOrder = new Order();
        newOrder.setBasket(basket);
        newOrder.setCreatedDate(new Date());
        newOrder.setCompany(company);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartProduct cartProduct : tempCartProducts) {
            CreateOrderItemDto newCreateOrderItemDto = new CreateOrderItemDto();
            newCreateOrderItemDto.setProductId(cartProduct.getProduct().getId());
            newCreateOrderItemDto.setQuantity(cartProduct.getQuantity());

            OrderItem newOrderItem = orderItemService.createOrderItem(newCreateOrderItemDto);

            orderItems.add(newOrderItem);
        }

        Long totalPrice = 0L;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        newOrder.setOrderItems(orderItems);
        newOrder.setTotalPrice(totalPrice);
        Order createdOrder = orderRepository.save(newOrder);
        basket.setOrder(createdOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(UpdateOrderDto orderDto) {
        Order order = findOrderById(orderDto.getOrderId());
        Cart cart = cartService.findCartByBasketId(order.getBasket().getId());

        List<CartProduct> tempCartProducts = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getQuantity() > 0) {
                tempCartProducts.add(cartProduct);
            }
        }
        if (tempCartProducts.isEmpty())
            return null;

        for (CartProduct cartProduct : tempCartProducts) {
            boolean productExists = false;
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED)
                    continue;
                if (cartProduct.getProduct().equals(orderItem.getProduct())) {
                    orderItem.setQuantity(orderItem.getQuantity() + cartProduct.getQuantity());
                    orderItem.setTotalPrice(orderItem.getQuantity() * orderItem.getProduct().getPrice());
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                CreateOrderItemDto newCreateOrderItemDto = new CreateOrderItemDto();
                newCreateOrderItemDto.setProductId(cartProduct.getProduct().getId());
                newCreateOrderItemDto.setQuantity(cartProduct.getQuantity());

                OrderItem newOrderItem = orderItemService.createOrderItem(newCreateOrderItemDto);

                order.getOrderItems().add(newOrderItem);
            }

        }
        Long totalPrice = 0L;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getStatus() != ORDER_STATUS.STATUS_COMPLETED)
                totalPrice += orderItem.getTotalPrice();
        }
        order.setUpdatedDate(new Date());
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(DeleteOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        if (order.getBasket() != null) {
            Basket basket = order.getBasket();
            basket.setIsActive(false);
            basket.setOrder(null);
        }
        if (order.getCompany() != null) {
            Company company = order.getCompany();
            company.getOrders().remove(order);
        }
        order.setBasket(null);
        order.setCompany(null);
        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());
        order.getOrderItems().clear();

        for (OrderItem orderItem : orderItems) {
            orderItemService.deleteOrderItem(new DeleteOrderItemDto(orderItem.getId()));
        }

        orderRepository.delete(order);
    }

    @Override
    public void completeOrder(CompleteOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        Date completedDate = new Date();
        Long totalPrice = 0L;
        Basket basket = order.getBasket();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setCompletedDate(completedDate);
            orderItem.setUpdatedDate(completedDate);
            orderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
            totalPrice += orderItem.getTotalPrice();
        }
        order.setUpdatedDate(completedDate);
        order.setCompletedDate(completedDate);
        order.setBasket(null);
        order.setTotalPrice(totalPrice);
        basket.getCart().getCartProducts().clear();
        basket.setIsActive(false);
        basket.setUpdatedDate(completedDate);
        orderRepository.save(order);
    }

    @Override
    public Order partialCompleteOrder(PartialCompleteOrderDto orderDto) {

        Order order = findOrderById(orderDto.getId());
        Date completedDate = new Date();

        for (OrderItem orderItem : orderDto.getOrderItems()) {
            if (orderItem.getQuantity() == 0) {
                continue;
            }
            OrderItem originalOrderItem = orderItemService.findOrderItemById(orderItem.getId());
            if (originalOrderItem.getQuantity() == orderItem.getQuantity()) {
                orderItem.setCompletedDate(completedDate);
                orderItem.setUpdatedDate(completedDate);
                orderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
                orderItemRepository.save(orderItem);

            } else {
                CreateOrderItemDto newCreateOrderItemDto = new CreateOrderItemDto();
                newCreateOrderItemDto.setProductId(orderItem.getProduct().getId());
                newCreateOrderItemDto.setQuantity(orderItem.getQuantity());
                OrderItem newOrderItem = orderItemService.createOrderItem(newCreateOrderItemDto);

                newOrderItem.setCreatedDate(orderItem.getCreatedDate());
                newOrderItem.setCompletedDate(completedDate);
                newOrderItem.setUpdatedDate(completedDate);
                newOrderItem.setStatus(ORDER_STATUS.STATUS_COMPLETED);
                order.getOrderItems().add(newOrderItem);
                orderItemService.updateOrderItem(new UpdateOrderItemDto(orderItem.getId(),
                        originalOrderItem.getQuantity() - orderItem.getQuantity()));

            }
        }
        Long totalPrice = 0L;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED)
                continue;
            totalPrice += orderItem.getTotalPrice();
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public Order transferOrder(TransferOrderDto orderDto) {
        Order curOrder = findOrderById(orderDto.getId());
        Basket curBasket = curOrder.getBasket();
        Basket newBasket = basketService.findBasketById(orderDto.getBasketId());
        Order newOrder = new Order();

        List<OrderItem> itemsToTransfer = new ArrayList<>();

        for (OrderItem orderItem : orderDto.getOrderItems()) {
            if (orderItem.getQuantity() == 0) {
                continue;
            }
            OrderItem originalOrderItem = orderItemService.findOrderItemById(orderItem.getId());
            if (originalOrderItem.getQuantity() == orderItem.getQuantity()) {
                itemsToTransfer.add(originalOrderItem);
                curOrder.getOrderItems().remove(originalOrderItem);
            } else {
                CreateOrderItemDto newCreateOrderItemDto = new CreateOrderItemDto();
                newCreateOrderItemDto.setProductId(orderItem.getProduct().getId());
                newCreateOrderItemDto.setQuantity(orderItem.getQuantity());
                OrderItem newOrderItem = orderItemService.createOrderItem(newCreateOrderItemDto);

                newOrderItem.setCreatedDate(orderItem.getCreatedDate());
                itemsToTransfer.add(newOrderItem);
                orderItemService.updateOrderItem(new UpdateOrderItemDto(orderItem.getId(),
                        originalOrderItem.getQuantity() - orderItem.getQuantity()));
            }
        }
        Long totalPrice = 0L;
        if (curOrder.getOrderItems().isEmpty()) {
            deleteOrder(new DeleteOrderDto(curOrder.getId()));
            curBasket.setOrder(null);
            curBasket.setIsActive(false);
        } else {
            for (OrderItem orderItem : curOrder.getOrderItems()) {
                if (orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED)
                    continue;
                totalPrice += orderItem.getTotalPrice();
            }
            curOrder.setTotalPrice(totalPrice);
        }
        totalPrice = 0L;
        for (OrderItem orderItem : itemsToTransfer) {
            if (orderItem.getStatus() == ORDER_STATUS.STATUS_COMPLETED)
                continue;
            totalPrice += orderItem.getTotalPrice();
        }

        newOrder.setOrderItems(itemsToTransfer);
        newOrder.setBasket(newBasket);
        newOrder.setCreatedDate(new Date());
        newOrder.setCompany(newBasket.getCompany());
        newOrder.setTotalPrice(totalPrice);
        newBasket.setOrder(newOrder);
        newBasket.setIsActive(true);
        orderRepository.save(newOrder);
        orderRepository.save(curOrder);

        return newOrder;
    }
}
