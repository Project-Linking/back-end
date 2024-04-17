package tukorea.projectlink.user.service;

import org.springframework.stereotype.Component;
import tukorea.projectlink.user.domain.Interests;
import tukorea.projectlink.user.dto.InterestsRequest;

import java.util.List;

@Component
public class InterestsMapper {
    public List<Interests> mapFrom(InterestsRequest interestsRequest) {
        return interestsRequest
                .interests()
                .stream()
                .map(Interests::new)
                .toList();
    }
}
