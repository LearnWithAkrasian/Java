package com.imran.servlet;

import com.imran.dto.ProductDto;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.ProductServImpl;
import com.imran.service.ProductService;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOGGER.info("Serving home page");
        List<ProductDto> allProducts = productService.findAllProductSortedByName();

        LOGGER.info("Total products found: {}", allProducts.size());
        req.setAttribute("products", allProducts);

        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
}
