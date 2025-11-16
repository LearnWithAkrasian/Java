package com.imran.filter;

import com.imran.util.SecurityContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

// This class filters or checks every client request starting with the "/" path.
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    // Access is restricted for users unless logged in, except for ALLOWED_CONTENTS.
    private static final String[] ALLOWED_CONTENTS
            = {".css", ".js", ".jpg", "home", "login", "signup"};

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        var httpSevletRequest = (HttpServletRequest)request;
        var responseUri = httpSevletRequest.getRequestURI();

        // Verify if the request URI is within ALLOWED_CONTENTS.
        boolean allowed
                = Stream.of(ALLOWED_CONTENTS)
                .anyMatch(responseUri::contains);
        // If the request URI is either "/" or the user is logged in, access to the path is allowed.
        // Otherwise, it will redirect to the login page.
        if (responseUri.equals("/") || allowed || SecurityContext.isAuthenticated(httpSevletRequest)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response)
                    .sendRedirect("/login");
        }

    }
}
