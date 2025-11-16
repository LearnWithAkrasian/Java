package com.imran.servlet;

import com.imran.dto.UserDto;
import com.imran.repository.UserRepositoryImpl;
import com.imran.service.UserService;
import com.imran.service.UserServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        Map<String, String> errors = validate(userDto);

        LOGGER.info("Testing validation of given user data.");
        if (errors.isEmpty()) {
            LOGGER.info("Creating a new user with: {}", userDto);
            userService.saveUser(userDto);
            resp.sendRedirect("/home");
        } else {
            LOGGER.info("User sent invalid data: {}", userDto);
            req.setAttribute("errors", errors);
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
        userDto.setConfirmPassword(req.getParameter("confirmPassword"));
        userDto.setUsername(req.getParameter("username"));

        return userDto;
    }

    private Map<String, String> validate(UserDto userDto) {
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<UserDto> violation : violations) {
            String path = violation.getPropertyPath().toString();
            if (errors.containsKey(path)) {
                String errorMsg = errors.get(path);
                errors.put(path, errorMsg + " <br/> " + violation.getMessage());
            }
            else {
                errors.put(path, violation.getMessage());
            }
        }
        return errors;
    }
}
