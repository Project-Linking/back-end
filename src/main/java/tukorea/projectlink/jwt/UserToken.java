package tukorea.projectlink.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserToken {
    private final String accessToken;
    private final String refreshToken;
}
