package com.imran.servlet;

import com.imran.dto.LoginDto;
import com.imran.exceptions.UserNotFoundException;
import com.imran.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LogIn extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogIn.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Serving Log in page");
        req.getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var loginDto =  new LoginDto(req.getParameter("username"),
                                     req.getParameter("password"));

        LOGGER.info("Received Login Dto: {}", loginDto);
        var errors = ValidationUtil.getInstance().validate(loginDto);

        if (!errors.isEmpty()) {
            LOGGER.info("Failed to login, sending login form again");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(req, resp);
            return; // if forwarding to login.jsp then rest of the code should not be executed. so returned.
        }

        try {
            login(loginDto, req);
            LOGGER.info("Logged in successfully, redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e) {
            LOGGER.error("Incorrect username or password", e);
            errors.put("username", e.getMessage());
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(req, resp);
        }
    }

    private void login(LoginDto loginDto, HttpServletRequest req) throws UserNotFoundException {

    }
}
