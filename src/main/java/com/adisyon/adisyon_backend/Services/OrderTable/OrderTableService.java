package com.adisyon.adisyon_backend.Services.OrderTable;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.OrderTable.CreateOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.DeleteOrderTableDto;
import com.adisyon.adisyon_backend.Dto.Request.OrderTable.UpdateOrderTableDto;
import com.adisyon.adisyon_backend.Entities.OrderTable;

public interface OrderTableService {

    public OrderTable createOrderTable(CreateOrderTableDto orderTableDto);

    public OrderTable updateOrderTable(UpdateOrderTableDto orderTableDto);

    public void deleteOrderTable(DeleteOrderTableDto orderTableDto);

    public void disableOrderTable(Long id);

    public OrderTable findOrderTableById(Long id);

    public List<OrderTable> findByCompanyId(Long id);

    // public OrderTableProduct addProductToTable(CreateOrderTableProductDto
    // pTableDto);

    // public OrderTableProduct updateProduct(UpdeteOrderTableProductDto pTableDto);

    // public OrderTable removeProductFromTable(Long productId);

    // public Long calculateOrderTableTotals(OrderTable orderTable);

    // public OrderTable findOrderTableById(Long id);

    // public OrderTable clearOrderTable(Long userId);
}
