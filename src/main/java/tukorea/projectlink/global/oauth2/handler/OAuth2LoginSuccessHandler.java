package tukorea.projectlink.global.oauth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.global.oauth2.service.CustomOAuth2UserService;
import tukorea.projectlink.global.oauth2.userinfo.OAuth2UserInfo;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;
import tukorea.projectlink.user.repository.UserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${client.url}")
    private String ClientUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2UserInfo oAuth2UserInfo = customOAuth2UserService.getOAuth2UserInfo();
        User user = findUser(oAuth2UserInfo);
        String accessToken = jwtService.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.createRefreshToken();
        jwtService.setJwtTokenToHeader(response, accessToken, refreshToken);
        response.sendRedirect(ClientUrl);
        user.updateRefreshToken(refreshToken);
    }

    public User findUser(OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.findBySocialId(oAuth2UserInfo.getId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
