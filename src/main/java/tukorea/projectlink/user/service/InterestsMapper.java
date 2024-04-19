package tukorea.projectlink.user.service;

import org.springframework.stereotype.Component;
import tukorea.projectlink.user.domain.Interests;
import tukorea.projectlink.user.domain.InterestsType;

import java.util.List;

@Component
public class InterestsMapper {
    public List<Interests> mapFrom(List<InterestsType> newInterests) {
        return newInterests
                .stream()
                .map(Interests::new)
                .toList();
    }

    public List<InterestsType> toInterestsType(List<Interests> interests) {
        return interests
                .stream()
                .map(Interests::getInterestsType)
                .toList();
    }
}
