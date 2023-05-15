package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
