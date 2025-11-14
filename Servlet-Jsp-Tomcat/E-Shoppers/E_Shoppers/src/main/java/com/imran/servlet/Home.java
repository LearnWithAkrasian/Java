package com.imran.servlet;

import com.imran.dto.ProductDto;
import com.imran.repository.DummyProductRepositoryImpl;
import com.imran.service.ProductServImpl;
import com.imran.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class Home extends HttpServlet {

    private ProductService productService
            = new ProductServImpl(new DummyProductRepositoryImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<ProductDto> allProducts = productService.findAllProductSortedByName();

        req.setAttribute("products", allProducts);

        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
}
