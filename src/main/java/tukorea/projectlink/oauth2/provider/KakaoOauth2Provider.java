package tukorea.projectlink.oauth2.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import tukorea.projectlink.global.configproperties.KakaoOauth2Properties;
import tukorea.projectlink.login.exception.Oauth2ErrorCode;
import tukorea.projectlink.login.exception.Oauth2Exception;
import tukorea.projectlink.oauth2.userinfo.KakaoUserInfo;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

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
        String accessToken = getOauth2Response(code).getAccessToken();
        System.out.println("accessToken = " + accessToken);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam(SECURE_RESOURCE, true).build())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .retrieve()
                .body(KakaoUserInfo.class);
    }


    private Oauth2ResponseToken getOauth2Response(String code) {
        MultiValueMap<String, String> pair = new LinkedMultiValueMap<>();
        pair.add("grant_type", GRANT_TYPE);
        pair.add("client_id", props.clientId());
        pair.add("redirect_uri", props.redirectUri());
        pair.add("client_secret", props.clientSecret());
        pair.add("code", code);
        return restClient.post()
                .uri(props.tokenUri())
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .body(pair)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new Oauth2Exception(Oauth2ErrorCode.INVALID_OAUTH_AUTHORIZATION_CODE);
                })
                .body(Oauth2ResponseToken.class);
    }
}
