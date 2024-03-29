package tukorea.projectlink.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestComment {
    private Long parentId;
    private Long boardId;
    private String content;

    @Builder
    public RequestComment(Long parentId, Long boardId, String content) {
        this.parentId = parentId;
        this.boardId = boardId;
        this.content = content;
    }
}
