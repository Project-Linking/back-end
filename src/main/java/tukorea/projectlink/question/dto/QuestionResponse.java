package tukorea.projectlink.question.dto;

import lombok.Builder;
import tukorea.projectlink.question.domain.Question;
import tukorea.projectlink.question.domain.QuestionType;

@Builder
public record QuestionResponse(
        String questionText,
        QuestionType type,
        Long boardId
) {
    public static QuestionResponse from(Question question){
        return QuestionResponse.builder()
                .boardId(question.getBoard().getId())
                .questionText(question.getQuestionText())
                .type(question.getType())
                .build();
    }
}
