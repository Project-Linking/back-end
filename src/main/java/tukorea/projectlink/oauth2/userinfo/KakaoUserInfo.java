package tukorea.projectlink.oauth2.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class KakaoUserInfo implements Oauth2UserInfo {
    @JsonProperty("id")
    private String socialLoginId;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Override
    public String getSocialId() {
        return socialLoginId;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.kakaoProfile.nickName;
    }

    @Override
    public String getImageUrl() {
        return kakaoAccount.kakaoProfile.imageUrl;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class KakaoAccount {
        @JsonProperty("profile")
        KakaoProfile kakaoProfile;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class KakaoProfile {
        @JsonProperty("nickname")
        private String nickName;

        @JsonProperty("profile_image_url")
        private String imageUrl;
    }
}
