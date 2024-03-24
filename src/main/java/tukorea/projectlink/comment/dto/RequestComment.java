package tukorea.projectlink.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestComment {
    private Long userId;
    private Long parentId;
    private Long boardId;
    private String content;
}
