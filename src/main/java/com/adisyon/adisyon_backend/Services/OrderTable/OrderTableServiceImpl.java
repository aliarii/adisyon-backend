package com.adisyon.adisyon_backend.Services.OrderTable;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.OrderTable.CreateOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.DeleteOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.UpdateOrderTableDto;
import com.adisyon.adisyon_backend.Entities.OrderTable;
import com.adisyon.adisyon_backend.Repositories.OrderTable.OrderTableRepository;
import com.adisyon.adisyon_backend.Services.Unwrapper;

@Service
public class OrderTableServiceImpl implements OrderTableService {

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Override
    public OrderTable findOrderTableById(Long id) {
        return Unwrapper.unwrap(orderTableRepository.findById(id), id);
    }

    @Override
    public OrderTable createOrderTable(CreateOrderTableDto orderTableDto) {

        OrderTable newOrderTable = new OrderTable();
        newOrderTable.setTableName("Table" + findByCompanyId(orderTableDto.getCompany().getId()).size() + 1);
        newOrderTable.setCompany(orderTableDto.getCompany());
        newOrderTable.setIsActive(false);
        newOrderTable.setCreatedDate(new Date());

        return orderTableRepository.save(newOrderTable);
    }

    @Override
    public OrderTable updateOrderTable(UpdateOrderTableDto orderTableDto) {

        OrderTable orderTable = findOrderTableById(orderTableDto.getOrderTableId());
        orderTable.setTableName(orderTableDto.getOrderTableName() != null ? orderTableDto.getOrderTableName()
                : orderTable.getTableName());
        orderTable.getOrderTableProducts().addAll(orderTableDto.getOrderTableProducts());
        orderTable.setUpdatedDate(new Date());
        orderTable.setIsActive(true);
        return orderTableRepository.save(orderTable);
    }

    @Override
    public void deleteOrderTable(DeleteOrderTableDto orderTableDto) {
        OrderTable orderTable = findOrderTableById(orderTableDto.getOrderTableId());
        orderTableRepository.delete(orderTable);
    }

    @Override
    public void disableOrderTable(Long id) {
        OrderTable orderTable = findOrderTableById(id);
        orderTable.setIsActive(false);
        orderTable.setUpdatedDate(new Date());
        orderTableRepository.save(orderTable);
    }

    @Override
    public List<OrderTable> findByCompanyId(Long id) {
        return orderTableRepository.findByCompanyId(id);
    }

}
