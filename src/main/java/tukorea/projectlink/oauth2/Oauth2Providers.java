package tukorea.projectlink.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tukorea.projectlink.login.exception.OAuthErrorCode;
import tukorea.projectlink.login.exception.OAuthException;
import tukorea.projectlink.oauth2.provider.Oauth2Provider;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Oauth2Providers {
    private final List<Oauth2Provider> providers;

    public Oauth2Provider findByProviderName(String providerName) {
        return providers
                .stream()
                .filter(provider -> provider.isEquals(providerName))
                .findFirst()
                .orElseThrow(() -> new OAuthException(OAuthErrorCode.CAN_NOT_FIND_OAUTH_PROVIDER));
    }
}
