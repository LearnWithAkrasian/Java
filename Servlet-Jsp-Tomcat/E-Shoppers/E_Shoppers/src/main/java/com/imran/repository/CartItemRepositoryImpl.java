package com.imran.repository;

import com.imran.domain.CartItem;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

// Communicates with the database for Cart Items domain.d
// Update Cart Item
// Save Cart Item
public class CartItemRepositoryImpl implements CartItemRepository {

    private static final Set<CartItem> CART_ITEMS = new CopyOnWriteArraySet<>();

    @Override
    public CartItem update(CartItem cartItem) {
        CART_ITEMS.add(cartItem);
        return cartItem;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        CART_ITEMS.add(cartItem);
        return cartItem;
    }

    @Override
    public void remove(CartItem cartItem) {
        CART_ITEMS.remove(cartItem);
    }
}
