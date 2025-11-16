package com.imran.util;


import com.imran.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityContext {
    public static final String AUTHENTICATION_KEY = "auth.key";

    public static void login(HttpServletRequest req,
                             User user) {

        // Getting old session and invalidating it.
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        // Putting user in the session
        HttpSession session = req.getSession(true);
        session.setAttribute(AUTHENTICATION_KEY, user);
    }

    public static void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.removeAttribute(AUTHENTICATION_KEY);
    }

    public static User getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(true);

        return (User) session.getAttribute(AUTHENTICATION_KEY);
    }
    public static boolean isAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession(true);

        return session.getAttribute(AUTHENTICATION_KEY) != null;
    }
}
