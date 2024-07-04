package tukorea.projectlink.group.dto;

import lombok.Builder;

@Builder
public record CreateGroupRequest(String groupName, Long maxUser, Long boardId) {
}
