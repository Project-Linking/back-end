package tukorea.projectlink.user.dto;

import tukorea.projectlink.user.domain.InterestsType;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;

import java.util.List;

public record InterestsRequest(List<InterestsType> interests) {
    public InterestsRequest {
        if (interests.size() > 3) {
            throw new UserException(UserErrorCode.INTERESTS_REQUEST_NOT_VALID);
        }
    }
}
