package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByQuestionId(Long questionId);

    long deleteByQuestionId(Long questionId);
}
