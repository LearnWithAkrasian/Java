package com.imran.util;

import com.imran.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityContext {
    public static final String AUTHENTICATION_KEY = "auth.key";

    public static void login(User user, HttpServletRequest req) {

        //get the old session and invalidate
        HttpSession session = req.getSession();
        if (session != null)
            session.invalidate();

        // get new session and set user to session object
        HttpSession newSession = req.getSession(true);
        newSession.setAttribute(AUTHENTICATION_KEY, user);
    }

    public static void logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
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
