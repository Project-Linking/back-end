package tukorea.projectlink.global.configproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.provider.kakao")
public record KakaoOauth2Properties(
        String clientId,
        String clientSecret,
        String redirectUri,
        String tokenUri,
        String userInfoUri,
        String providerName
) {
}
