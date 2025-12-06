package com.imran.servlet;

import com.imran.repository.CartItemRepositoryImpl;
import com.imran.repository.CartRepository;
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

@WebServlet("/checkout")
public class CheckOut extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckOut.class);

    private CartService cartService
            = new CartServiceImpl(
                    new CartRepositoryImpl(),
                    new DummyProductRepositoryImpl(),
                    new CartItemRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Checking out a cart");
        var currentUser = SecurityContext.getCurrentUser(req);
        var cart = cartService.getCartByUser(currentUser);

        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/checkout.jsp")
                .forward(req, resp);
    }
}
