package tukorea.projectlink.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.login.dto.LoginRequest;
import tukorea.projectlink.oauth2.Oauth2Providers;
import tukorea.projectlink.oauth2.provider.Oauth2Provider;
import tukorea.projectlink.oauth2.userinfo.Oauth2UserInfo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final Oauth2Providers oauth2Providers;

    public Oauth2UserInfo login(String providerName, LoginRequest loginRequest) {
        Oauth2Provider provider = oauth2Providers.findByProviderName(providerName);
        return provider.getUserInfo(loginRequest.authorizationCode());

    }

}
