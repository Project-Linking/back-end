package tukorea.projectlink.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.question.domain.Question;
import tukorea.projectlink.question.dto.QuestionRequest;
import tukorea.projectlink.question.dto.QuestionResponse;
import tukorea.projectlink.question.repository.QuestionRepository;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.service.UserService;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final BoardRepository boardRepository;
    private final UserService userService;

    public QuestionResponse registerQuestion(Authentication authentication, QuestionRequest questionRequest) {
        Board board = boardRepository.findById(questionRequest.boardId()).orElseThrow(() -> new RuntimeException("Board not found"));
        User user = userService.getUser(authentication.userId());

        Question questionBuilder = Question.builder()
                .type(questionRequest.type())
                .user(user)
                .board(board)
                .questionText(questionRequest.questionText())
                .build();

        Question question = questionRepository.save(questionBuilder);

        return QuestionResponse.from(question);
    }
}
