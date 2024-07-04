package tukorea.projectlink.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.global.jwt.UserToken;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.dto.LoginResponse;
import tukorea.projectlink.login.dto.Oauth2LoginResponse;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
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
        log.info("Created RefreshToken:{}", oauth2LoginResponse.userToken().getRefreshToken());
        responseCookieLoader.LoadCookie(response, oauth2LoginResponse.userToken());
        return CommonResponse.successWithData(oauth2LoginResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = loginService.login(loginRequest);
        log.info("Created RefreshToken : {}", loginResponse.userToken().getRefreshToken());
        responseCookieLoader.LoadCookie(response, loginResponse.userToken());
        return CommonResponse.successWithData(loginResponse);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<?> logout(@Auth Authentication authentication) {
        log.info("Logout:{}", authentication.userId());
        loginService.removeRefreshToken(authentication.userId());
        return CommonResponse.successWithEmptyData();
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    //TODO refreshToken 인덱스 걸고 성능 측정 + 인덱스 잘 타는지 검사
    public CommonResponse<UserToken> reissueTokens(@CookieValue("refresh-token") final String refreshToken, HttpServletResponse response) {
        log.info("RefreshToken:{}", refreshToken);
        UserToken userToken = loginService.reissueUserToken(refreshToken);
        responseCookieLoader.LoadCookie(response, userToken);
        return CommonResponse.successWithData(userToken);
    }
}
