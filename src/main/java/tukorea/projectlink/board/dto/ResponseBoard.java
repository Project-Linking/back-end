package tukorea.projectlink.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseBoard {
    private Long id;
    private String title;
    private String content;
}
