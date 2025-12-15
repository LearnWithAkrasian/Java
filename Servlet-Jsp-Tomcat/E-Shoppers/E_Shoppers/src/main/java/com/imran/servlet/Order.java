package com.imran.servlet;

import com.imran.domain.ShippingAddress;
import com.imran.dto.ShippingAddressDto;
import com.imran.repository.CartItemRepositoryImpl;
import com.imran.repository.CartRepositoryImpl;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.CartService;
import com.imran.service.CartServiceImpl;
import com.imran.service.OrderService;
import com.imran.util.SecurityContext;
import com.imran.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Handles order-related requests.
 * GET  /order  -> Displays the order page with cart details and shipping form
 * POST /order  -> Processes the submitted shipping information and places order
 */
@WebServlet("/order")
public class Order extends HttpServlet {

    /** Logger for debugging and error tracking */
    private static final Logger LOGGER = LoggerFactory.getLogger(Order.class);

    /**
     * Cart service used to retrieve cart information for the current user.
     * Dependencies are manually instantiated here.
     */
    private CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
            new DummyProductRepositoryImpl(),
            new CartItemRepositoryImpl());

    /** Service responsible for processing orders */
    private OrderService orderService;

    /**
     * Handles GET requests.
     * Loads cart data, country list, and forwards to the order JSP page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Add current user's Cart to the request (if authenticated)
        addCartToUi(req);

        // Provide list of available countries for shipping form
        // which will be shown in the drop-down list
        req.setAttribute("countries", getCountries());

        req.getRequestDispatcher("/WEB-INF/order.jsp")
                .forward(req, resp);
    }

    /**
     * Handles POST requests.
     * Validates shipping address and processes the order if valid.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Build ShippingAddress object from request parameters
        var shippingAddress = getShippingAddress(req);

        // Validate shipping address fields
        var errors = ValidationUtil.getInstance().validate(shippingAddress);

        // If validation errors exist, return user to order page
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);

            addCartToUi(req);
            req.setAttribute("countries", getCountries());

            req.getRequestDispatcher("/WEB-INF/order.jsp")
                    .forward(req, resp);
        }
        // If validation passes, process the order
        else {
            orderService.processOrder(
                    shippingAddress,
                    SecurityContext.getCurrentUser(req)
            );

            // Redirect to home page with order confirmation status
            resp.sendRedirect("/home?orderStatus=confirmed");
        }
    }

    /**
     * Adds the current user's cart to the request scope
     * so it can be displayed on the UI.
     */
    private void addCartToUi(HttpServletRequest req) {
        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }
    }

    /**
     * Creates and populates a ShippingAddress object
     * from HTTP request parameters.
     */
    private ShippingAddressDto getShippingAddress(HttpServletRequest req) {
        var shippingAddress = new ShippingAddressDto();

        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setCity(req.getParameter("city"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setZip(req.getParameter("zip"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));

        return shippingAddress;
    }

    /**
     * Returns a static list of supported countries
     * for shipping selection.
     */
    private List<String> getCountries() {
        return List.of("Bangladesh", "Switzerland", "Japan", "USA", "Canada");
    }
}
