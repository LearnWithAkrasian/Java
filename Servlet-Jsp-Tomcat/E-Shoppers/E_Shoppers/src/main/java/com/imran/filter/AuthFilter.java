package com.imran.filter;

import com.imran.util.SecurityContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    private static final String[] ALLOWED_CONTENTS
            = {".css", ".js", "home", ".jpg", "login", "signup"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest)servletRequest;

        // Get the exact URL user is requesting
        var requestedUri = httpServletRequest.getRequestURI();

        // Check if the requested URI contains any allowed keyword
        boolean allowed = Stream.of(ALLOWED_CONTENTS)
                          .anyMatch(requestedUri::contains);

        // Allow the request to pass if:
        // 1. user requested "/"
        // 2. the URL is publicly allowed (CSS, JS, login, signup, images)
        // 3. OR user is already authenticated
        if (requestedUri.equals("/")
                || allowed
                || SecurityContext.isAuthenticated(httpServletRequest)) {

            // Continue normal request flow
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            // Otherwise redirect unauthenticated user to login page
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }
}
