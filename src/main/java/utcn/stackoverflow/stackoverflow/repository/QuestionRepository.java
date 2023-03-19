package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    long deleteByQuestionId(Long questionId);
}
