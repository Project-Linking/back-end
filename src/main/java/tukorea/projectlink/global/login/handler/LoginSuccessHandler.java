package tukorea.projectlink.global.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.user.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        String loginId = principal.getUsername();
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));
        String accessToken = jwtService.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.createRefreshToken();
        response.getWriter().write("AT: "+accessToken+"\n");
        response.getWriter().write("RT: "+refreshToken);
        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);
        jwtService.setJwtTokenToHeader(response,accessToken,refreshToken);
    }
}
