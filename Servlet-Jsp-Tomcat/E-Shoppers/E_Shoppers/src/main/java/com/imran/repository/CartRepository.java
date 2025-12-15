package com.imran.repository;

import com.imran.domain.Cart;
import com.imran.domain.CartItem;
import com.imran.domain.User;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findByUser(User currentUser);
    Cart save(Cart cart);
    Cart update(Cart cart);
    Optional<CartItem> findCartItem(Long productId, Cart cart);
}
