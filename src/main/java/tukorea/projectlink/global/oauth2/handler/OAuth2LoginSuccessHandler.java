package tukorea.projectlink.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.global.oauth2.CustomOAuth2User;
import tukorea.projectlink.user.Role;
import tukorea.projectlink.user.repository.UserRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
        // 첫 OAuth2 로그인인 경우 추가 입력 폼으로 리다이렉트
        if(oAuth2User.getRole()==Role.GUEST){
            String accessToken = jwtService.createAccessToken(UUID.randomUUID().toString());
            response.addHeader(jwtService.getAccessTokenHeader(), "Bearer " + accessToken);
            response.sendRedirect("/oauth/sign-up");
        }else{
            loginSuccess(response,oAuth2User);
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getNickname());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessTokenHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshTokenHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        userRepository.findByNickname(oAuth2User.getNickname())
                        .ifPresent(user-> user.updateRefreshToken(refreshToken));
    }
}
