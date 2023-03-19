package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Content;

@Repository
public interface ContentRepository extends CrudRepository<Content, Long> {
}
