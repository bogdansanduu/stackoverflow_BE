package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    long deleteByAnswerId(Long answerId);
}
