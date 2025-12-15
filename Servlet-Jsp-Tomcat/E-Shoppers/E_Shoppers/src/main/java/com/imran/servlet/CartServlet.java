package com.imran.servlet;

import com.imran.domain.Cart;
import com.imran.domain.User;
import com.imran.enums.Action;
import com.imran.repository.CartItemRepositoryImpl;
import com.imran.repository.CartRepositoryImpl;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.CartService;
import com.imran.service.CartServiceImpl;
import com.imran.util.SecurityContext;
import com.imran.util.StringUtil;
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
        var action = req.getParameter("action");
        var cart = getCart(req);

        if (StringUtil.isNotEmpty(action)) {
            processCart(productId, action, cart);

            resp.sendRedirect("/checkout");
            return;
        }
        LOGGER.info("Received cart request to add a product with id: {} ", productId);

        cartService.addProductToCart(productId, cart);
        resp.sendRedirect("/home");
    }

    // Retrieve the cart associated with current User of request object
    private Cart getCart(HttpServletRequest req) {
        final User currentUser = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }

    // here defined the logics of checkout.jsp '-', '+', and 'Remove' will work.
    private void processCart(String productId, String action, Cart cart) {
        switch (Action.valueOf(action.toUpperCase())) {
            case ADD:
                LOGGER.info("Received request to add a product with id: {} ", productId);
                cartService.addProductToCart(productId, cart);
                break;
            case REDUCE:
                LOGGER.info("Received request to reduce a product with id: {} ", productId);
                cartService.reduceProductToCart(productId, cart);
                break;
            case REMOVE:
                LOGGER.info("Received request to remove a cart item with product id: {} ", productId);
                cartService.removeCartItemFromCart(productId, cart);
        }
    }
}
