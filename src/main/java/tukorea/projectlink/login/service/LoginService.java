package tukorea.projectlink.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.jwt.JwtService;
import tukorea.projectlink.jwt.UserToken;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.oauth2.Oauth2Providers;
import tukorea.projectlink.oauth2.provider.Oauth2Provider;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;
import tukorea.projectlink.user.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final Oauth2Providers oauth2Providers;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserToken socialLogin(String providerName, SocialLoginRequest loginRequest) {
        Oauth2Provider provider = oauth2Providers.findByProviderName(providerName);
        Oauth2UserInfo oauth2UserInfo = provider.getUserInfo(loginRequest.authorizationCode());
        User user = userService.findOrSaveOauthUser(oauth2UserInfo);
        return issueUserToken(user);
    }

    @Transactional
    public UserToken login(LoginRequest loginRequest) {
        User user = userService.getUser(loginRequest.loginId());
        return verifyPassword(loginRequest, user);
    }

    private UserToken verifyPassword(LoginRequest loginRequest, User user) {
        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return issueUserToken(user);
        } else {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    @Transactional
    public UserToken issueUserToken(User user) {
        UserToken userToken = jwtService.createUserToken(user.getId());
        user.updateRefreshToken(userToken.getRefreshToken());
        return userToken;
    }

}
