package tukorea.projectlink.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
