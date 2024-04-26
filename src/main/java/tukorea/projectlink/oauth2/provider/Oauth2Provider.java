package tukorea.projectlink.oauth2.provider;

import org.springframework.stereotype.Component;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@Component
public interface Oauth2Provider {
    boolean isEquals(String providerName);

    Oauth2UserInfo getUserInfo(String code);
}
