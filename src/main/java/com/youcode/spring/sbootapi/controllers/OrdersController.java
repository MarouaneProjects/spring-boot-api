package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.dtos.request.orders.CheckoutDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderListResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderSingleResponse;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.OrdersService;
import com.youcode.spring.sbootapi.services.SettingsService;
import com.youcode.spring.sbootapi.services.auth.AuthorizationService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import com.youcode.spring.sbootapi.dtos.request.orders.CheckoutDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderListResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderSingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final SettingsService settingsService;
    private final OrdersService ordersService;
    private final UsersService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    public OrdersController(SettingsService settingsService, OrdersService ordersService,
                            UsersService userService) {
        this.ordersService = ordersService;
        this.settingsService = settingsService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<AppResponse> index(HttpServletRequest request,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "page_size", defaultValue = "30") int pageSize) {

        if (this.userService.isAnonymous())
            return new ResponseEntity<>(new ErrorResponse("We can not retrieve orders for anonymous users"), HttpStatus.FORBIDDEN);

        Page<Order> ordersPage = this.ordersService.findOrderSummariesBelongingToUser(this.userService.getCurrentLoggedInUser(), page, pageSize);
        return ResponseEntity.ok().body(OrderListResponse.build(ordersPage, request.getRequestURI()));
    }

    @GetMapping("{id}")
    public OrderSingleResponse show(@PathVariable("id") Long id, Principal principal) {
        User user = userService.getCurrentLoggedInUser();

        if (user == null)
            throw new PermissionDeniedException("Not logged in");

        Order order = ordersService.findById(id);
        if (!order.getUser().getId().equals(user.getId()) || !userService.isUserAdmin((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()))
            throw new PermissionDeniedException("You are not authorized");
        return OrderSingleResponse.build(order);
    }

    @PostMapping
    public AppResponse checkout(@RequestBody CheckoutDto form) {
        Order order = this.ordersService.save(form, userService.getCurrentLoggedInUser());
        return OrderSingleResponse.build(order);
    }
}
