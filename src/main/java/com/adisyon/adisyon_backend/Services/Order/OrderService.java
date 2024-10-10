package com.adisyon.adisyon_backend.Services.Order;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Entities.Order;

public interface OrderService {
    public Order findOrderById(Long id);

    public Order findOrderByBasketId(Long id);

    public List<Order> findOrdersByCompanyId(Long id);

    public Order createOrder(CreateOrderDto orderDto);

    public Order updateOrder(UpdateOrderDto orderDto);

    public void deleteOrder(DeleteOrderDto orderDto);
}