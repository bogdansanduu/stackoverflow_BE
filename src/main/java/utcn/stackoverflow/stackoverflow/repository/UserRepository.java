package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utcn.stackoverflow.stackoverflow.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    long deleteByUserId(Long userId);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);



}
