package com.adisyon.adisyon_backend.Services.OrderItem;

import com.adisyon.adisyon_backend.Dto.Request.OrderItem.CreateOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.DeleteOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.UpdateOrderItemDto;
import com.adisyon.adisyon_backend.Entities.OrderItem;

public interface OrderItemService {
    public OrderItem findOrderItemById(Long id);

    public OrderItem createOrderItem(CreateOrderItemDto orderDto);

    public OrderItem updateOrderItem(UpdateOrderItemDto orderDto);

    public void deleteOrderItem(DeleteOrderItemDto orderDto);

    public OrderItem completeOrderItem(Long id);

}
