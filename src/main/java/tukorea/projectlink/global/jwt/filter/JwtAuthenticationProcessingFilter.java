package tukorea.projectlink.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import tukorea.projectlink.global.PasswordUtil;
import tukorea.projectlink.global.jwt.exception.JwtErrorCode;
import tukorea.projectlink.global.jwt.exception.JwtException;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.user.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // "/login" 이외의 모든 요청은 현재 필터를 거쳐 refresh 토큰을 검사한다.
        log.info("request URL {}",request.getRequestURI());
        if(request.getRequestURI().equals("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        String refreshToken = jwtService.extractTokenFromRequestHeader(request, jwtService.getRefreshTokenHeader())
                .filter(jwtService::isValidToken)
                .orElse(null);

        if(refreshToken != null){
            checkRefreshTokenToDb(refreshToken,response);
            return;
        }

        checkAccessToken(request, response, filterChain);
    }

    private void checkAccessToken(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException, JwtException {
        jwtService.extractTokenFromRequestHeader(request, jwtService.getAccessTokenHeader())
                .flatMap(jwtService::extractUserUniqueId)
                .flatMap(userRepository::findByLoginId)
                .ifPresentOrElse(this::saveAuthentication,()->log.info("missmatch accesstoken"));
        filterChain.doFilter(request,response);
    }
    private void saveAuthentication(User myUser) {
        String password = myUser.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = PasswordUtil.getRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLoginId())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkRefreshTokenToDb(String refreshToken,HttpServletResponse response) throws JwtException{
        if(jwtService.isValidToken(refreshToken)){
            userRepository.findByRefreshToken(refreshToken)
                    .ifPresentOrElse(
                            user->reissueTokens(user, jwtService.createRefreshToken(),response),
                            ()->new JwtException(JwtErrorCode.MISMATCH_REFRESHTOKEN)
                            );
        }
    }

    private void reissueTokens(User user, String refreshToken, HttpServletResponse response){
        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);
        jwtService.sendAccessAndRefreshToken(
                response,
                jwtService.createAccessToken(user.getNickname()),
                jwtService.createRefreshToken()
        );
    }

}
