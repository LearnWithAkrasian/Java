package com.imran.repository;

import com.imran.domain.CartItem;

public interface CartItemRepository {
    CartItem update(CartItem cartItem);
    CartItem save(CartItem cartItem);
}
