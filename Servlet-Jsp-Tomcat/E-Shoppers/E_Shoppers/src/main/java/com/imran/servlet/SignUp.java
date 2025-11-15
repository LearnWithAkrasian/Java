package com.imran.servlet;

import com.imran.dto.UserDto;
import com.imran.repository.UserRepositoryImpl;
import com.imran.service.UserService;
import com.imran.service.UserServiceImpl;
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
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Serving sign up page");

        req.getRequestDispatcher("/WEB-INF/signup.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserDto userDto = copyParametersTo(req);

        if (isValid(userDto)) {
            LOGGER.info("Creating a new user with: {}", userDto);
            userService.saveUser(userDto);
            resp.sendRedirect("/home");
        } else {
            LOGGER.info("User sent invalid data: {}", userDto);
            req.getRequestDispatcher("/WEB-INF/signup.jsp")
                    .forward(req, resp);
        }
    }

    private UserDto copyParametersTo(HttpServletRequest req) {
        var userDto = new UserDto();
        userDto.setFirstName(req.getParameter("firstName"));
        userDto.setLastName(req.getParameter("lastName"));
        userDto.setEmail(req.getParameter("email"));
        userDto.setPassword(req.getParameter("password"));
        userDto.setConfirmPassword(req.getParameter("passwordConfirm"));
        userDto.setUsername(req.getParameter("username"));

        return userDto;
    }

    private boolean isValid(UserDto userDto) {
        return true;
    }
}
