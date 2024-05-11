package tukorea.projectlink.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import tukorea.projectlink.jwt.JwtProvider;
import tukorea.projectlink.global.exception.JwtCustomException;
import tukorea.projectlink.global.errorcode.JwtErrorCode;
import tukorea.projectlink.global.errorcode.UserErrorCode;
import tukorea.projectlink.global.exception.UserException;

@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER = "Bearer";
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new UserException(UserErrorCode.INVALID_REQUEST);
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = extractTokenFromHeader(header);
        jwtProvider.checkAccessToken(accessToken);
        String subject = jwtProvider.getSubject(accessToken);

        return new Authentication(Long.parseLong(subject));
    }

    private String extractTokenFromHeader(String header) {
        if (header.startsWith(BEARER)) {
            return header.substring(BEARER.length()).trim();
        } else {
            throw new JwtCustomException(JwtErrorCode.INVALID_HEADER_TYPE);
        }
    }
}
