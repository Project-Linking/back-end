package tukorea.projectlink.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.jwt.UserToken;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final ResponseCookieLoader responseCookieLoader;

    @PostMapping("/login/{providerName}")
    public CommonResponse<UserToken> socialLogin(
            @PathVariable String providerName,
            @RequestBody SocialLoginRequest loginRequest,
            HttpServletResponse response) {
        UserToken userToken = loginService.socialLogin(providerName, loginRequest);
        responseCookieLoader.LoadCookie(response, userToken);
        return CommonResponse.successWithData(userToken);
    }

    @PostMapping("/login")
    public CommonResponse<UserToken> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        UserToken userToken = loginService.login(loginRequest);
        responseCookieLoader.LoadCookie(response, userToken);
        return CommonResponse.successWithData(userToken);
    }
}
