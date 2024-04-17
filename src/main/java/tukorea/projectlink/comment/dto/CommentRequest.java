package tukorea.projectlink.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    private Long parentId;
    @NotNull(message = "board 값이 없습니다.")
    private Long boardId;
    @NotNull(message = "content 값이 없습니다.")
    private String content;
}
