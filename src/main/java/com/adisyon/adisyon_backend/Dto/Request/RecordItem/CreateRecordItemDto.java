package com.adisyon.adisyon_backend.Dto.Request.RecordItem;

import java.util.ArrayList;
import java.util.List;

import com.adisyon.adisyon_backend.Entities.OrderItem;
import com.adisyon.adisyon_backend.Entities.PAYMENT_TYPE;
import com.adisyon.adisyon_backend.Entities.RECORD_ITEM_TYPE;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecordItemDto {

    @NotNull
    private RECORD_ITEM_TYPE recordItemType;

    @Nullable
    private Long currentBasketId;

    @Nullable
    private Long targetBasketId;

    @Nullable
    private List<OrderItem> orderItems = new ArrayList<>();

    @Nullable
    private Long payAmount;

    @Nullable
    private PAYMENT_TYPE paymentType;

    // ORDER_ADD,
    // ORDER_REMOVE,
    // ORDER_UPDATE,
    // ORDER_CANCEL,
    // ORDER_COMPLETE,
    // ORDER_TRANSFER_ALL,
    // ORDER_TRANSFER_SELECTIVELY,
    // ORDER_PAY_ALL,
    // ORDER_PAY_SELECTIVELY,
    // ORDER_PAY_AMOUNT,
}
