package utcn.stackoverflow.stackoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utcn.stackoverflow.stackoverflow.entity.Vote;

public interface VotesRepository extends JpaRepository<Vote, Long> {
    Vote findById_UserIdAndId_ContentId(Long userId, Long contentId);
    @Query("select sum(a.value) from Vote a where a.content.contentId = ?1")
    Long getVotesValue(Long contentId);
}
