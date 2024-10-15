package com.adisyon.adisyon_backend.Services.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.CreateOrderItemDto;
import com.adisyon.adisyon_backend.Entities.Basket;
import com.adisyon.adisyon_backend.Entities.Cart;
import com.adisyon.adisyon_backend.Entities.CartProduct;
import com.adisyon.adisyon_backend.Entities.Company;
import com.adisyon.adisyon_backend.Entities.Order;
import com.adisyon.adisyon_backend.Entities.OrderItem;
import com.adisyon.adisyon_backend.Repositories.Order.OrderRepository;
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

        Order newOrder = new Order();
        newOrder.setBasket(basket);
        newOrder.setCreatedDate(new Date());
        newOrder.setCompany(company);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProducts()) {
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

        for (CartProduct cartProduct : cart.getCartProducts()) {
            boolean productExists = false;
            for (OrderItem orderItem : order.getOrderItems()) {
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
            totalPrice += orderItem.getTotalPrice();
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(DeleteOrderDto orderDto) {
        Order order = findOrderById(orderDto.getId());
        orderRepository.delete(order);
    }

}
