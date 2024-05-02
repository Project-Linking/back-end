package tukorea.projectlink.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResponseBoard {
    private Long id;

    @NotBlank
    @Size(min = 1, max = 30)
    private String title;

    @Size(max = 3000)
    private String content;
    private LocalDateTime deadline;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
