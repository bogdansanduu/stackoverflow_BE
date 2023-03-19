package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
    Tag findByTagId(Long tagId);
}
