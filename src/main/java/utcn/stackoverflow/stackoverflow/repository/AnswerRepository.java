package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByAnswerId(Long answerId);
    @Query("select a from Answer a where a.question.questionId = ?1")
    List<Answer> findByQuestionId(Long questionId);
    long deleteByAnswerId(Long answerId);

}
