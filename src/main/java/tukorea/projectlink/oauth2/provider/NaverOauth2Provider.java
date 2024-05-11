package tukorea.projectlink.oauth2.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tukorea.projectlink.login.dto.SocialLoginRequest;
import tukorea.projectlink.global.configproerties.NaverOauth2Properties;
import tukorea.projectlink.oauth2.userinfo.NaverUserInfo;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class NaverOauth2Provider implements Oauth2Provider {
    private static final String GRANT_TYPE = "authorization_code";
    private final RestClient client;
    private final NaverOauth2Properties props;

    @Override
    public boolean isEquals(String providerName) {
        return props.providerName().equals(providerName);
    }

    @Override
    public Oauth2UserInfo getUserInfo(SocialLoginRequest loginRequest) {
        NaverTokenResponse response = getAccessToken(loginRequest.authorizationCode());
        return client.get()
                .uri(props.userInfoUri())
                .headers(header -> header.setBearerAuth(response.accessToken))
                .retrieve()
                .body(NaverUserInfo.class);
    }

    private NaverTokenResponse getAccessToken(String authenticationCode) {
        return client.get()
                .uri(createURI(authenticationCode))
                .retrieve()
                .body(NaverTokenResponse.class);
    }

    private URI createURI(String authenticationCode) {
        String stateCode = createStateCode();
        return UriComponentsBuilder.fromUri(URI.create(props.tokenUri()))
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", props.clientId())
                .queryParam("client_secret", props.clientSecret())
                .queryParam("code", authenticationCode)
                .queryParam("state", stateCode)
                .build()
                .toUri();
    }

    private String createStateCode() {
        return UUID.randomUUID().toString();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    private static class NaverTokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("expires_in")
        private String expiresIn;
    }
}
