package com.adisyon.adisyon_backend.Controllers.OrderTable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.OrderTable.CreateOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.DeleteOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.UpdateOrderTableDto;
import com.adisyon.adisyon_backend.Entities.OrderTable;
import com.adisyon.adisyon_backend.Services.OrderTable.OrderTableService;

@RestController
@RequestMapping("/api/orderTables")
public class OrderTableController {

    @Autowired
    private OrderTableService orderTableService;

    @GetMapping
    public ResponseEntity<List<OrderTable>> getAllTables(@PathVariable Long id) {
        List<OrderTable> orderTables = orderTableService.findByCompanyId(id);
        return new ResponseEntity<>(orderTables, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTable> getOrderTableById(@PathVariable Long id) {
        OrderTable orderTable = orderTableService.findOrderTableById(id);
        return new ResponseEntity<>(orderTable, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderTable> createOrderTable(@RequestBody CreateOrderTableDto orderTableDto) {
        OrderTable newOrderTable = orderTableService.createOrderTable(orderTableDto);
        return new ResponseEntity<>(newOrderTable, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderTable> updateOrderTable(@RequestBody UpdateOrderTableDto orderTableDto) {
        OrderTable updatedOrderTable = orderTableService.updateOrderTable(orderTableDto);
        return new ResponseEntity<>(updatedOrderTable, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteOrderTable(@RequestBody DeleteOrderTableDto orderTableDto) {
        orderTableService.deleteOrderTable(orderTableDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> disableOrderTable(@PathVariable Long id) {
        orderTableService.disableOrderTable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
