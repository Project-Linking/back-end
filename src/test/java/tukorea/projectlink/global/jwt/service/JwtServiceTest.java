package tukorea.projectlink.global.jwt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @BeforeEach
    void setUp() throws IllegalAccessException {
        ReflectionTestUtils.setField(jwtService, "jwtSecret", "44WB44WI44S344S566i47J6z65+s66iA44WQ44WI44S365+s44WQ44WR44WB44WIO+OFk+OEtzvrnpjjhZHjhZPjhYHsnqzjhZHrn6zrjJzjhZDjhYHsoIDrnrTjhZDjhYjrqLjrjIDjhZHroILrp6TjhZHjhZPrnpjjhZHjhLfjhYjrqLjjhZDjhZHrn7zjhYjrjIg=");
        ReflectionTestUtils.setField(jwtService, "accessTokenExpirationPeriod", 10000L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExpirationPeriod", 10000L);
        ReflectionTestUtils.setField(jwtService, "accessTokenHeader", "Authorization");
        ReflectionTestUtils.setField(jwtService, "refreshTokenHeader", "Authorization-refresh");
    }

    @Test
    @DisplayName("생성한 액세스 토큰은 유효한 토큰이여야한다.")
    void createAccessToken() {
        // given
        String userId = "1";
        String accessToken = jwtService.createAccessToken(userId);
        // when
        boolean isValidToken = jwtService.isValidToken(accessToken);
        // then
        Assertions.assertThat(isValidToken).isTrue();
    }

    @Test
    @DisplayName("생성한 리프레쉬 토큰은 유효한 토큰이여야한다.")
    void createRefreshToken() {
        // given
        String accessToken = jwtService.createRefreshToken();
        // when
        boolean isValidToken = jwtService.isValidToken(accessToken);
        // then
        Assertions.assertThat(isValidToken).isTrue();
    }

    @Test
    @DisplayName("헤더에 설정한 액세스 토큰과 리프레쉬 토큰은 헤더에서 추출한 토큰과 같아야한다.")
    void setTokenToHeader() {
        //given
        MockHttpServletResponse res = new MockHttpServletResponse();
        String userId = "1";
        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken();
        jwtService.setJwtTokenToHeader(res, accessToken, refreshToken);
        //when
        String extractedAccessToken = res.getHeader("Authorization");
        String extractedRefreshToken = res.getHeader("Authorization-refresh");
        //then
        Assertions.assertThat(extractedAccessToken).isEqualTo(accessToken);
        Assertions.assertThat(extractedRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("액세스 토큰 생성 시 넣은 클레임과 추출한 클레임은 같아야 한다.")
    void checkClaim() {
        //given
        String userId = "1";
        String accessToken = jwtService.createAccessToken(userId);
        //when
        Optional<String> s = jwtService.extractUserUniqueId(accessToken);
        //then
        Assertions.assertThat(s.get()).isEqualTo(userId);
    }


}