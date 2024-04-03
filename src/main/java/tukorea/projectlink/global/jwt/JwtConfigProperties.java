package tukorea.projectlink.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Getter
public class JwtConfigProperties {
    private final String secretKey;
    private final Long accessExpiration;
    private final String accessHeader;
    private final Long refreshExpiration;
    private final String refreshHeader;
}
