package tukorea.projectlink.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Table(name="USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password; // 비밀번호
    private String nickname; // 닉네임
    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    @Builder(builderMethodName = "signupBuilder")
    public User(String loginId, String password, String nickname, Role role, PasswordEncoder passwordEncoder) {
        this.loginId = loginId;
        this.password = passwordEncode(password,passwordEncoder);
        this.nickname = nickname;
        this.role = role;
    }

    @Builder
    public User(String nickname, Role role, SocialType socialType, String socialId) {
        this.nickname = nickname;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    // 비밀번호 암호화 메소드
    private String passwordEncode(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
