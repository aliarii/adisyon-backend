package com.adisyon.adisyon_backend.Controllers.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Order.PayAllOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.CreateOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.DeleteOrderDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PaySelectedOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.PayWithoutOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.TransferOrdersDto;
import com.adisyon.adisyon_backend.Dto.Request.Order.UpdateOrderDto;
import com.adisyon.adisyon_backend.Entities.Order;
import com.adisyon.adisyon_backend.Services.Order.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/basket/{id}")
    public ResponseEntity<Order> getOrderByBasketId(@PathVariable Long id) {
        Order order = orderService.findOrderByBasketId(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Order>> getOrdersByCompanyId(@PathVariable Long id) {
        List<Order> orders = orderService.findOrdersByCompanyId(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDto orderDto) {
        Order order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody UpdateOrderDto orderDto) {
        Order orders = orderService.updateOrder(orderDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<HttpStatus> deleteOrder(@RequestBody DeleteOrderDto orderDto) {
        orderService.deleteOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/pay/all")
    public ResponseEntity<Order> payAllOrders(@RequestBody PayAllOrdersDto orderDto) {
        Order order = orderService.payAllOrders(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/pay/selected")
    public ResponseEntity<Order> paySelectedOrders(@RequestBody PaySelectedOrdersDto orderDto) {
        Order order = orderService.paySelectedOrders(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/pay/without-order")
    public ResponseEntity<Order> payWithoutOrders(@RequestBody PayWithoutOrdersDto orderDto) {
        Order order = orderService.payWithoutOrders(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/transfer")
    public ResponseEntity<Order> transferOrders(@RequestBody TransferOrdersDto orderDto) {
        Order order = orderService.transferOrders(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
