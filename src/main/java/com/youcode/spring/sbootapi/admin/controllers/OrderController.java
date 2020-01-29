package com.youcode.spring.sbootapi.admin.controllers;

import com.youcode.spring.sbootapi.admin.dtos.AdminOrderUpdateDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderListResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderSingleResponse;
import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.services.OrdersService;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderListResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderSingleResponse;
import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/orders")
public class OrderController {
    @Autowired
    private OrdersService ordersService;


    @GetMapping
    public AppResponse index(HttpServletRequest request,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "page_size", defaultValue = "30") int pageSize) {

        page = Math.max(1, page); // Ensure min 1
        pageSize = Math.max(1, pageSize); // Ensure pageSize min 1

        Page<Order> orders = ordersService.findLatest(page, Math.max(1, pageSize));

        return OrderListResponse.build(orders, request.getRequestURI());
    }

    @PutMapping("{id}")
    public AppResponse checkout(@PathVariable("id") Long id, @RequestBody AdminOrderUpdateDto form) {
        Order order = this.ordersService.update(id, form);
        return OrderSingleResponse.build(order);
    }


}
