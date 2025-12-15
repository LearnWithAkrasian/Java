package com.imran.servlet;

import com.imran.domain.Cart;
import com.imran.dto.ProductDto;
import com.imran.repository.CartItemRepositoryImpl;
import com.imran.repository.CartRepositoryImpl;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.CartService;
import com.imran.service.CartServiceImpl;
import com.imran.service.ProductServImpl;
import com.imran.service.ProductService;
import com.imran.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class Home extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Home.class);

    private ProductService productService
            = new ProductServImpl(new DummyProductRepositoryImpl());
    private CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
              new DummyProductRepositoryImpl(),
              new CartItemRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.info("Serving home page");
        final String attribute = req.getParameter("orderStatus");
        if (attribute != null && attribute.equals("confirmed")) {
            req.setAttribute("message", "<strong>Congratulations!</strong> Your order has been confirmed!");
        }
        List<ProductDto> allProducts = productService.findAllProductSortedByName();
        LOGGER.info("Total products found: {}", allProducts.size());


        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            req.setAttribute("cart", cartService.getCartByUser(currentUser));
        }
        req.setAttribute("products", allProducts);

        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
}
