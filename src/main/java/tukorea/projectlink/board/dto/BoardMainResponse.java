package tukorea.projectlink.board.dto;

import lombok.Builder;
import tukorea.projectlink.board.domain.Board;

import java.time.LocalDateTime;

@Builder
public record BoardMainResponse(
        String title,

        String userName,
        LocalDateTime createdAt,
        Long commentNum
) {
    public static BoardMainResponse toResponse(Board board){
        return BoardMainResponse.builder()
                .createdAt(board.getCreatedAt())
                .userName(board.getUser().getNickname())
                .title(board.getTitle())
                .commentNum((long) board.getComments().size())
                .build();
    }
}
