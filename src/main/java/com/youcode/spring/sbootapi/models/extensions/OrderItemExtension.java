package com.youcode.spring.sbootapi.models.extensions;

import com.youcode.spring.sbootapi.models.OrderItem;

public class OrderItemExtension extends OrderItem {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }
}
