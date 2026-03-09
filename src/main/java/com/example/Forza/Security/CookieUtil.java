package com.example.Forza.Security;

import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void setJwtCookie(HttpServletResponse response, String token) {

        String cookieHeader = String.format(
                "accessToken=%s; " +
                        "Max-Age=%d; " +
                        "Path=/; " +
                        "HttpOnly; " +
                        "SameSite=None; " +
                        "Secure",
                token,
                86400
        );

        response.addHeader("Set-Cookie", cookieHeader);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        String cookieHeader = "accessToken=; Max-Age=0; Path=/; HttpOnly; SameSite=None; Secure";
        response.addHeader("Set-Cookie", cookieHeader);
    }
}