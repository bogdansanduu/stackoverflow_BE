package utcn.stackoverflow.stackoverflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.VoteDto;
import utcn.stackoverflow.stackoverflow.entity.Vote;
import utcn.stackoverflow.stackoverflow.repository.VotesRepository;

@Service
public class VotesService {

    @Autowired
    VotesRepository votesRepository;

    public VoteDto getVote(Long userId, Long contentId) {
        Vote userContent = votesRepository.findById_UserIdAndId_ContentId(userId, contentId);

        if (userContent == null)
            return null;

        return new VoteDto(userContent.getUser().getUserId(), userContent.getContent().getUser().getUserId(), userContent.getValue());
    }
}
