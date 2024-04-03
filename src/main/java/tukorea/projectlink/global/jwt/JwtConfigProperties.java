package tukorea.projectlink.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Getter
public class JwtConfigProperties {
    private final String secretKey;
    private final Access access;
    private final Refresh refresh;

    @Getter
    public static class Access {
        Long expiration;
        String header;

        public Access(Long expiration, String header) {
            this.expiration = expiration;
            this.header = header;
        }
    }

    @Getter
    public static class Refresh {
        Long expiration;
        String header;

        public Refresh(Long expiration, String header) {
            this.expiration = expiration;
            this.header = header;
        }
    }
}
