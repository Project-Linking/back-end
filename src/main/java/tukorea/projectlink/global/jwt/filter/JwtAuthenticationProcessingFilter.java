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
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tukorea.projectlink.global.PasswordUtil;
import tukorea.projectlink.global.jwt.JwtConfigProperties;
import tukorea.projectlink.global.jwt.exception.JwtErrorCode;
import tukorea.projectlink.global.jwt.exception.JwtException;
import tukorea.projectlink.global.jwt.service.JwtService;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final JwtConfigProperties props;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        log.info("request URL {}", request.getRequestURI());
        // public api(인증이 필요없는 api) 는 해당 필터를 거치지 않도록 한다.
        if (antPathMatcher.match("/api/public/**", request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional<String> refreshToken = jwtService.extractTokenFromRequestHeader(request, props.getRefresh().getHeader())
                .filter(jwtService::isValidToken);

        if (refreshToken.isPresent()) {
            checkRefreshTokenToDb(refreshToken.get(), response);
            return;
        }

        checkAccessToken(request, response, filterChain);
    }

    private void checkAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        jwtService.extractTokenFromRequestHeader(request, props.getAccess().getHeader())
                .flatMap(jwtService::extractUserUniqueId)
                .flatMap(id -> userRepository.findById(Long.parseLong(id)))
                .ifPresentOrElse(this::saveAuthentication, () -> {
                    throw new JwtException(JwtErrorCode.MISMATCH_ACCESSTOKEN);
                });
        filterChain.doFilter(request, response);
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

    private void checkRefreshTokenToDb(String refreshToken, HttpServletResponse response) throws JwtException {
        if (jwtService.isValidToken(refreshToken)) {
            userRepository.findByRefreshToken(refreshToken)
                    .ifPresentOrElse(
                            user -> reissueTokens(user, jwtService.createRefreshToken(), response),
                            () -> new JwtException(JwtErrorCode.MISMATCH_REFRESHTOKEN)
                    );
        }
    }

    private void reissueTokens(User user, String refreshToken, HttpServletResponse response) {
        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);
        jwtService.setJwtTokenToHeader(
                response,
                jwtService.createAccessToken(String.valueOf(user.getId())),
                jwtService.createRefreshToken()
        );
    }

}
