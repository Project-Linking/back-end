package tukorea.projectlink.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.user.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private List<Interests> interests = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String nickname;
    private String imageUri;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String socialId;
    private String refreshToken;

    @Builder
    public User(String loginId, String password, String nickname, Role role, String socialId, String refreshToken, List<Interests> interests, String imageUri) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
        this.interests = interests;
        this.imageUri = imageUri;
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void removeRefreshToken() {
        this.refreshToken = null;
    }

    public void updateInterests(List<InterestsType> newTypes) {
        this.interests.removeAll(removeInterests(newTypes));
        this.interests.addAll(addInterests(newTypes));
    }

    private List<Interests> addInterests(List<InterestsType> newTypes) {
        return newTypes
                .stream()
                .filter(isDifferentWithNewTypes())
                .map(Interests::new)
                .toList();
    }

    private List<Interests> removeInterests(List<InterestsType> newTypes) {
        return this.interests
                .stream()
                .filter(isDifferentWithOldTypes(newTypes))
                .toList();
    }

    private Predicate<InterestsType> isDifferentWithNewTypes() {
        return newType -> !this.interests
                .stream()
                .map(Interests::getInterestsType)
                .toList()
                .contains(newType);
    }

    private Predicate<Interests> isDifferentWithOldTypes(List<InterestsType> newTypes) {
        return oldType -> !newTypes.contains(oldType.getInterestsType());
    }
}
