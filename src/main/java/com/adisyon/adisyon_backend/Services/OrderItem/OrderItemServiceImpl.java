package com.adisyon.adisyon_backend.Services.OrderItem;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.OrderItem.CreateOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.DeleteOrderItemDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderItem.UpdateOrderItemDto;
import com.adisyon.adisyon_backend.Entities.ORDER_STATUS;
import com.adisyon.adisyon_backend.Entities.OrderItem;
import com.adisyon.adisyon_backend.Entities.Product;
import com.adisyon.adisyon_backend.Repositories.OrderItem.OrderItemRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;
import com.adisyon.adisyon_backend.Services.Product.ProductService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Override
    public OrderItem findOrderItemById(Long id) {
        return Unwrapper.unwrap(orderItemRepository.findById(id), id);
    }

    @Override
    public OrderItem createOrderItem(CreateOrderItemDto orderDto) {
        Product product = productService.findProductById(orderDto.getProductId());
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setProduct(product);
        newOrderItem.setQuantity(orderDto.getQuantity());
        newOrderItem.setTotalPrice(orderDto.getQuantity() * product.getPrice());
        newOrderItem.setCreatedDate(new Date());
        newOrderItem.setStatus(ORDER_STATUS.STATUS_PENDING);
        return orderItemRepository.save(newOrderItem);
    }

    @Override
    public OrderItem updateOrderItem(UpdateOrderItemDto orderDto) {
        OrderItem orderItem = findOrderItemById(orderDto.getId());
        orderItem.setUpdatedDate(new Date());
        orderItem.setQuantity(orderDto.getQuantity());
        orderItem.setTotalPrice(orderDto.getQuantity() * orderItem.getProduct().getPrice());
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(DeleteOrderItemDto orderDto) {
        OrderItem orderItem = findOrderItemById(orderDto.getId());
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItem completeOrderItem(Long id) {
        return null;
    }

}
