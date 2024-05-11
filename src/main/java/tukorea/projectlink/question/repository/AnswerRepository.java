package tukorea.projectlink.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.question.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
