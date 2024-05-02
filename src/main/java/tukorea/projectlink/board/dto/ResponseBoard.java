package tukorea.projectlink.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResponseBoard {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime deadline;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
