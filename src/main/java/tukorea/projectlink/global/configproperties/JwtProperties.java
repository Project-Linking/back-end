package tukorea.projectlink.global.configproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        Long accessExpiration,
        Long refreshExpiration
) {
}
