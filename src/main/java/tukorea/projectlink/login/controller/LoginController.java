package tukorea.projectlink.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.service.LoginService;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/{providerName}")
    public CommonResponse<Oauth2UserInfo> login(
            @PathVariable String providerName,
            @RequestBody LoginRequest loginRequest) {
        Oauth2UserInfo userInfo = loginService.login(providerName, loginRequest);
        return CommonResponse.successWithData(userInfo);
    }
}
