package tukorea.projectlink.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tukorea.projectlink.login.exception.Oauth2ErrorCode;
import tukorea.projectlink.login.exception.Oauth2Exception;
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
                .orElseThrow(() -> new Oauth2Exception(Oauth2ErrorCode.CAN_NOT_FIND_OAUTH_PROVIDER));
    }
}
