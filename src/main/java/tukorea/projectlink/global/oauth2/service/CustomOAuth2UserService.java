package tukorea.projectlink.global.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import tukorea.projectlink.global.oauth2.CustomOAuth2User;
import tukorea.projectlink.global.oauth2.OAuthAttributes;
import tukorea.projectlink.global.oauth2.userinfo.OAuth2UserInfo;
import tukorea.projectlink.user.SocialType;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";
    private final UserRepository userRepository;
    private OAuth2UserInfo oAuth2UserInfo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
        oAuth2UserInfo = extractAttributes.getOauth2UserInfo();

        User createdUser = getUser(extractAttributes, socialType); // getUser() 메소드로 User 객체 생성 후 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getNickname(),
                createdUser.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if (NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        } else
            return SocialType.KAKAO;

    }

    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        User findUser = userRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getOauth2UserInfo().getId()).orElse(null);
        if (findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }

    public OAuth2UserInfo getoAuth2UserInfo() {
        return oAuth2UserInfo;
    }
}
