package com.imran.servlet;

import com.imran.domain.Cart;
import com.imran.domain.User;
import com.imran.repository.CartItemRepositoryImpl;
import com.imran.repository.CartRepositoryImpl;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.CartService;
import com.imran.service.CartServiceImpl;
import com.imran.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Handles the request by the uri "/add-to-cart
@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    // By Injecting CartServiceImpl object to cartService object
    // ensuring that CartService Interface will use the implementation of CartServiceImpl
    private CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
                                  new DummyProductRepositoryImpl(),
                                  new CartItemRepositoryImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var productId = req.getParameter("productId");
        LOGGER.info("Received cart request to add a product with id: {} ", productId);

        var cart = getCart(req);
        cartService.addProductToCart(productId, cart);
        resp.sendRedirect("/home");
    }

    // Retrieve the cart associated with current User of request object
    private Cart getCart(HttpServletRequest req) {
        final User currentUser = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }
}
