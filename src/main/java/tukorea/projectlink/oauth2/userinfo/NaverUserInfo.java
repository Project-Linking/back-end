package tukorea.projectlink.oauth2.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverUserInfo implements Oauth2UserInfo {
    @JsonProperty("response")
    private Response response;

    @Override
    public String getSocialId() {
        return response.socialLoginId;
    }

    @Override
    public String getNickname() {
        return response.nickName;
    }

    @Override
    public String getImageUrl() {
        return response.imageUrl;
    }

    private static class Response {
        @JsonProperty("id")
        private String socialLoginId;
        @JsonProperty("nickname")
        private String nickName;
        @JsonProperty("profile_image")
        private String imageUrl;
    }
}
