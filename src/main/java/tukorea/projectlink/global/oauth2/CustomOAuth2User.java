package tukorea.projectlink.global.oauth2;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import tukorea.projectlink.user.Role;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private final String nickname;
    private final Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String nickname, Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.nickname = nickname;
        this.role = role;
    }
}
