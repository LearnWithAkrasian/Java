package com.imran.repository;

import com.imran.domain.Cart;
import com.imran.domain.Order;
import com.imran.domain.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Set<Order> ORDERS = new CopyOnWriteArraySet<>();

    @Override
    public Order save(Order order) {
        ORDERS.add(order);
        return order;
    }

    @Override
    public Set<Order> findOrderByUser(User currentUser) {
        // Create a new Set to store orders belonging to the given user
        // This avoids exposing the internal ORDERS collection
        Set<Order> userOrders = new HashSet<>();

        // If the user is null, return an empty set
        // Prevents NullPointerException and invalid searches
        if (currentUser == null) {
            return userOrders;
        }

        // Iterate through all stored orders
        for (Order order : ORDERS) {

            // Check if the order belongs to the given user
            // Using equals() ensures logical comparison
            if (currentUser.equals(order.getUser())) {

                // Add the order to the result set
                userOrders.add(order);
            }
        }

        // Return all orders associated with the given user
        return userOrders;
    }
}
