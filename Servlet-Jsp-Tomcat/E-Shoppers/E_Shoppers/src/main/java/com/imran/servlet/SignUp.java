package com.imran.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUp extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUp.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Serving home page");

        req.getRequestDispatcher("/WEB-INF/signup.jsp")
                .forward(req, resp);

    }
}
