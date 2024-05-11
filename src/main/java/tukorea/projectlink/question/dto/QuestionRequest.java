package tukorea.projectlink.question.dto;

import tukorea.projectlink.question.domain.QuestionType;
public record QuestionRequest(
        Long boardId,
        String questionText,
        QuestionType type
) {
}
