package com.youcode.spring.sbootapi.services.interfaces;

import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService extends CrudService<Order>{
    Page<Order> findOrderSummariesBelongingToUser(User user, int page, int count);

    Page<Order> findOrderDetailsBelongingToUser(User user, int page, int count);

    Order save(Order order);

    Order findById(Long id);

    Order findById(Long id, boolean throwExceptionIfNotFound);

    Order findByIdOrNull(Long id);

    Order findByIdOrThrow(Long id);

    List<Order> saveAll(List<Order> orders);
}
