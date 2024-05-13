package tukorea.projectlink.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import tukorea.projectlink.jwt.UserToken;

@Component
public class ResponseCookieLoader {
    public static final int COOKIE_AGE_SECONDS = 604800;

    public void LoadCookie(HttpServletResponse response, UserToken userToken) {
        ResponseCookie responseCookie = makeCookie(userToken);
        response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    private ResponseCookie makeCookie(UserToken userToken) {
        return ResponseCookie.from("refresh-token", userToken.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
