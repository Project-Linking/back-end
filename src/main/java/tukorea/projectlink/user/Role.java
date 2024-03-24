package tukorea.projectlink.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    GUEST("GUSET"),
    USER("USER");

    private final String key;
}
