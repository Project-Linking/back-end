package tukorea.projectlink.oauth2.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.client.RestClient;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2.provider.naver")
public class NaverOauth2Provider implements Oauth2Provider {
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
        return null;
    }
}
