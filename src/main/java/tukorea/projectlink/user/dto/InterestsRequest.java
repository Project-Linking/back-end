package tukorea.projectlink.user.dto;

import tukorea.projectlink.user.domain.InterestsType;

import java.util.List;

public record InterestsRequest(List<InterestsType> interests) {
}
