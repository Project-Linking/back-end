package tukorea.projectlink.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.jwt.UserToken;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.dto.Oauth2LoginResponse;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final ResponseCookieLoader responseCookieLoader;

    @PostMapping("/login/{providerName}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Oauth2LoginResponse> socialLogin(
            @PathVariable String providerName,
            @RequestBody SocialLoginRequest loginRequest,
            HttpServletResponse response) {
        Oauth2LoginResponse oauth2LoginResponse = loginService.socialLogin(providerName, loginRequest);
        responseCookieLoader.LoadCookie(response, oauth2LoginResponse.userToken());
        return CommonResponse.successWithData(oauth2LoginResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<UserToken> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        UserToken userToken = loginService.login(loginRequest);
        responseCookieLoader.LoadCookie(response, userToken);
        return CommonResponse.successWithData(userToken);
    }
}
