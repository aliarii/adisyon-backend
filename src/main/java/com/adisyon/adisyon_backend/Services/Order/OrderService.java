package com.adisyon.adisyon_backend.Services.Order;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Order.PayAllOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderByCartDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PaySelectedOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PayAmountDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.TransferOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderByCartDto;
import com.adisyon.adisyon_backend.Entities.Order;

public interface OrderService {
    public Order findOrderById(Long id);

    public Order findOrderByBasketId(Long id);

    public List<Order> findOrdersByCompanyId(Long id);

    public Order createOrder(CreateOrderDto orderDto);

    public Order createOrderByCart(CreateOrderByCartDto orderDto);

    public Order updateOrder(UpdateOrderDto orderDto);

    public Order updateOrderByCart(UpdateOrderByCartDto orderDto);

    public void deleteOrder(DeleteOrderDto orderDto);

    public Order payAllOrders(PayAllOrdersDto orderDto);

    public Order paySelectedOrders(PaySelectedOrdersDto orderDto);

    public Order payAmount(PayAmountDto orderDto);

    public Order transferOrders(TransferOrdersDto orderDto);

    public Order updateTotalPrice(Long id);
}
