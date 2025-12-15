package com.imran.repository;

import com.imran.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
