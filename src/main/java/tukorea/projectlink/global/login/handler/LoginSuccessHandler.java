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
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        String loginId = principal.getUsername();
        User user = userRepository.findByLoginId(loginId)
                // TODO: 여기도 Custom RuntimeException 감싸
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));
        String accessToken = jwtService.createAccessToken(user.getLoginId());
        String refreshToken = jwtService.createRefreshToken();
        response.getWriter().write("AT: "+accessToken+"\n");
        response.getWriter().write("RT: "+refreshToken);
        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);
        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);
    }
}
