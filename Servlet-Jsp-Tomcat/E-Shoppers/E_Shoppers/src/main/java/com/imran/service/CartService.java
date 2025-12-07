package com.imran.service;

import com.imran.domain.Cart;
import com.imran.domain.User;

public interface CartService {
    Cart getCartByUser(User user);
    void  addProductToCart(String productId, Cart cart);

    void removeProductToCart(String productId, Cart cart);
}
