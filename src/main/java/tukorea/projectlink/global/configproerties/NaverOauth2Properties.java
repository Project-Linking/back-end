package tukorea.projectlink.global.configproerties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.provider.naver")
public record NaverOauth2Properties(
        String clientSecret,
        String clientId,
        String redirectUri,
        String tokenUri,
        String userInfoUri,
        String providerName
) {
}
