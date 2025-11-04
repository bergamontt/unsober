package ua.unsober.backend.common.utils.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class CookieUtils {
    public static Optional<String> getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return Optional.empty();
        for (Cookie c : cookies) {
            if ("jwt".equals(c.getName())) {
                return Optional.of(c.getValue());
            }
        }
        return Optional.empty();
    }
}
