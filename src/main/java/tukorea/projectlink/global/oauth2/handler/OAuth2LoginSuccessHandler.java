package tukorea.projectlink.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.global.oauth2.CustomOAuth2User;
import tukorea.projectlink.global.oauth2.service.CustomOAuth2UserService;
import tukorea.projectlink.global.oauth2.userinfo.OAuth2UserInfo;
import tukorea.projectlink.user.domain.User;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        // 첫 OAuth2 로그인인 경우 추가 입력 폼으로 리다이렉트
//        if(oAuth2User.getRole()==Role.GUEST){
        // 임시토큰
//            String accessToken = jwtService.createAccessToken(UUID.randomUUID().toString());
//            response.addHeader(jwtService.getAccessTokenHeader(), "Bearer " + accessToken);
//        }else{
        loginSuccess(response, oAuth2User);
//        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        OAuth2UserInfo oAuth2UserInfo = customOAuth2UserService.getoAuth2UserInfo();
        User user = userRepository.findBySocialId(oAuth2UserInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));
        String accessToken = jwtService.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.createRefreshToken();
        jwtService.setJwtTokenToHeader(response, accessToken, refreshToken);
        response.sendRedirect(ClientUrl);
        user.updateRefreshToken(refreshToken);
    }
}
