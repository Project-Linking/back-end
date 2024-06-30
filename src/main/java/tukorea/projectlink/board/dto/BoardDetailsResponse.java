package tukorea.projectlink.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.enums.Category;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.ResponseComment;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Builder
public record BoardDetailsResponse(
    Long id,
    Long userId,

    @NotBlank(message = "제목은 반드시 입력해야 합니다.")
    @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
    String title,

    @Size(max = 3000, message = "글 내용은 최대 3000자까지 가능합니다.")
    String content,

    @NotNull(message = "존재하지 않는 카테고리입니다.")
    Set<Category> category,

    Long likeNum,

    LocalDateTime deadline,

    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    List<ResponseComment> comments
) {
    public static BoardDetailsResponse toResponseDetails(Board board, List<ResponseComment> comments) {

        return BoardDetailsResponse.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .likeNum(board.getLikeNum())
                .deadline(board.getDeadline())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .comments(comments)
                .build();
    }
}

