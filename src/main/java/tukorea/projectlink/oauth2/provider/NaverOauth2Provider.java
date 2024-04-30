package tukorea.projectlink.oauth2.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tukorea.projectlink.global.configproperties.NaverOauth2Properties;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@RequiredArgsConstructor
@Component
public class NaverOauth2Provider implements Oauth2Provider {
    private final RestClient client;
    private final NaverOauth2Properties props;

    @Override
    public boolean isEquals(String providerName) {
        return props.providerName().equals(providerName);
    }

    @Override
    public Oauth2UserInfo getUserInfo(String code) {
        return null;
    }
}
