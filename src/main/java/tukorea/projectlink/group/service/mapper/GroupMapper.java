package tukorea.projectlink.group.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tukorea.projectlink.board.service.BoardService;
import tukorea.projectlink.group.domain.Group;
import tukorea.projectlink.group.dto.CreateGroupRequest;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final BoardService boardService;

    public Group mapFrom(CreateGroupRequest createGroupRequest) {
        return Group.builder()
                .groupName(createGroupRequest.groupName())
                .maxUsers(createGroupRequest.maxUser())
                .build();
    }
}
