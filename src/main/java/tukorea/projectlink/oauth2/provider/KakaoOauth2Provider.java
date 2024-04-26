package tukorea.projectlink.oauth2.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import tukorea.projectlink.login.exception.OAuthErrorCode;
import tukorea.projectlink.login.exception.OAuthException;
import tukorea.projectlink.oauth2.userinfo.KakaoUserInfo;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2.provider.kakao")
public class KakaoOauth2Provider implements Oauth2Provider {
    private static final String GRANT_TYPE = "authorization_code";
    private static final String SECURE_RESOURCE = "secure_resource";
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;
    private final String providerName;
    private final RestClient client;

    @Override
    public boolean isEquals(String providerName) {
        return this.providerName.equals(providerName);
    }

    @Override
    public Oauth2UserInfo getUserInfo(String code) {
        String accessToken = getAccessToken(code);
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(userInfoUrl)
                        .queryParam(SECURE_RESOURCE, true)
                        .build())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(KakaoUserInfo.class);
    }


    private String getAccessToken(String code) {
        return client.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(RequestBody.builder()
                        .grantType(GRANT_TYPE)
                        .clientId(clientId)
                        .redirectUri(redirectUrl)
                        .code(code))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new OAuthException(OAuthErrorCode.INVALID_OAUTH_AUTHORIZATION_CODE);
                })
                .body(String.class);
    }

    @Builder
    private static class RequestBody {
        private String grantType;
        private String clientId;
        private String redirectUri;
        private String code;
    }
}
