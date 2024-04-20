package tukorea.projectlink.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tukorea.projectlink.user.domain.InterestsType.*;

class UserTest {

    @Test
    @DisplayName("새로운 관심분야에 없는 관심분야는 기존 관심분야에서 제거된다.")
    void test() {
        // given
        List<Interests> originInterests = new ArrayList<>(List.of(new Interests(SPRING), new Interests(DJANGO), new Interests(VUE)));
        
        List<InterestsType> newInterests = Arrays.asList(SPRING, DJANGO, REACT);
        User user = User.builder()
                .interests(originInterests)
                .build();

        // when
        user.updateInterests(newInterests);

        // then
        List<InterestsType> originTypes = originInterests.stream().map(Interests::getInterestsType).toList();
        assertThat(originTypes).containsExactlyElementsOf(newInterests);
    }

}