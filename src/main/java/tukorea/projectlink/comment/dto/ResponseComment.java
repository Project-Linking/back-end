package tukorea.projectlink.comment.dto;

import lombok.Builder;
import lombok.Getter;
import tukorea.projectlink.comment.domain.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class ResponseComment {

    private Long id;
    private String content;
    private Long userId;
    private Long boardId;
    private Long parentId;
    private List<ResponseComment> replies;

    public ResponseComment(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.replies = Optional.ofNullable(comment.getReplies())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ResponseComment::new)
                .collect(Collectors.toList());
    }
}
