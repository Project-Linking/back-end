package tukorea.projectlink.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.projectlink.global.configproerties.JwtProperties;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final JwtProperties props;

    public UserToken createUserToken(Long userId) {
        String accessToken = jwtProvider.create(String.valueOf(userId), props.accessExpiration());
        String refreshToken = jwtProvider.create(String.valueOf(userId), props.refreshExpiration());
        return new UserToken(accessToken, refreshToken);
    }
}
