package tukorea.projectlink.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import tukorea.projectlink.comment.dto.ResponseComment;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BoardDetailsResponse(
    Long id,
    Long userId,

    @NotBlank(message = "제목은 반드시 입력해야 합니다.")
    @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
    String title,

    @Size(max = 3000, message = "글 내용은 최대 3000자까지 가능합니다.")
    String content,

    LocalDateTime deadline,

    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    List<ResponseComment> comments
) {}

