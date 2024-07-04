package tukorea.projectlink.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.global.errorcode.UserErrorCode;
import tukorea.projectlink.global.exception.UserException;
import tukorea.projectlink.global.jwt.JwtService;
import tukorea.projectlink.global.jwt.UserToken;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.dto.LoginResponse;
import tukorea.projectlink.login.dto.Oauth2LoginResponse;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.oauth2.Oauth2Providers;
import tukorea.projectlink.oauth2.provider.Oauth2Provider;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private static final String UTF_8 = "UTF-8";
    private final Oauth2Providers oauth2Providers;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Oauth2LoginResponse socialLogin(String providerName, SocialLoginRequest loginRequest) {
        Oauth2Provider provider = oauth2Providers.findByProviderName(providerName);
        Oauth2UserInfo oauth2UserInfo = provider.getUserInfo(loginRequest);
        User user = userService.findOrSaveOauthUser(oauth2UserInfo);
        UserToken userToken = issueUserToken(user);
        return new Oauth2LoginResponse(userToken, oauth2UserInfo.getNickname(), oauth2UserInfo.getImageUrl());
    }

    public UserToken issueUserToken(User user) {
        UserToken userToken = jwtService.createUserToken(user.getId());
        user.updateRefreshToken(userToken.getRefreshToken());
        return userToken;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.getUser(loginRequest.loginId());
        UserToken userToken = verifyPassword(loginRequest, user);
        return new LoginResponse(userToken, user.getNickname());
    }

    private UserToken verifyPassword(LoginRequest loginRequest, User user) {
        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return issueUserToken(user);
        } else {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    // TODO Transactional read only?
    public void removeRefreshToken(Long userId) {
        User user = userService.getUser(userId);
        user.removeRefreshToken();
    }

    public UserToken reissueUserToken(String refreshToken) {
        User user = userService.checkRefreshToken(refreshToken);
        return issueUserToken(user);
    }

}
