package com.imran.repository;

import com.imran.domain.Order;
import com.imran.domain.User;

import java.util.Set;

public interface OrderRepository {
    Order save(Order order);
    Set<Order> findOrderByUser(User currentUser);
}
