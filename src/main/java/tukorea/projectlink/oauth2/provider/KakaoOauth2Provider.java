package tukorea.projectlink.oauth2.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tukorea.projectlink.login.exception.Oauth2ErrorCode;
import tukorea.projectlink.login.exception.Oauth2Exception;
import tukorea.projectlink.oauth2.configproperties.KakaoOauth2Properties;
import tukorea.projectlink.oauth2.userinfo.KakaoUserInfo;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class KakaoOauth2Provider implements Oauth2Provider {
    private static final String GRANT_TYPE = "authorization_code";
    private static final String SECURE_RESOURCE = "secure_resource";
    private final KakaoOauth2Properties props;
    private final RestClient restClient;

    @Override
    public boolean isEquals(String providerName) {
        return props.providerName().equals(providerName);
    }

    @Override
    public Oauth2UserInfo getUserInfo(String code) {
        String accessToken = getAccessToken(code).getAccessToken();
        return restClient.get()
                .uri(createURI())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .retrieve()
                .body(KakaoUserInfo.class);
    }

    private URI createURI() {
        return UriComponentsBuilder
                .fromUri(URI.create(props.userInfoUri()))
                .queryParam(SECURE_RESOURCE, true)
                .build()
                .toUri();
    }


    private KakaoTokenResponse getAccessToken(String code) {
        return restClient.post()
                .uri(props.tokenUri())
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .body(createParams(code))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new Oauth2Exception(Oauth2ErrorCode.INVALID_OAUTH_AUTHORIZATION_CODE);
                })
                .body(KakaoTokenResponse.class);
    }

    private MultiValueMap<String, String> createParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", props.clientId());
        params.add("redirect_uri", props.redirectUri());
        params.add("client_secret", props.clientSecret());
        params.add("code", code);
        return params;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    private static class KakaoTokenResponse {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private int expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("refresh_token_expires_in")
        private String refreshTokenExpiresIn;
    }
}
