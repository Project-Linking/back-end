package tukorea.projectlink.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.enums.Category;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record BoardMainResponse(
        Long id,

        @NotBlank(message = "제목은 반드시 입력해야 합니다.")
        @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
        String title,

        Set<Category> categories,

        Long likeNum,

        String userName,
        LocalDateTime createdAt,
        Long commentNum
) {
    public static BoardMainResponse toResponseMain(Board board){
        return BoardMainResponse.builder()
                .id(board.getId())
                .createdAt(board.getCreatedAt())
                .userName(board.getUser().getNickname())
                .title(board.getTitle())
                .categories(board.getCategory())
                .likeNum(board.getLikeNum())
                .commentNum((long) board.getComments().size())
                .build();
    }
}
