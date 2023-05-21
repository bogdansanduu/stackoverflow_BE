package utcn.stackoverflow.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.Question;

@Getter
@Setter
@AllArgsConstructor
public class AnswerDTO implements Comparable<AnswerDTO> {
    private Long id;
    private Question question;
    private Content content;
    private UserDTO creator;
    private Long voteCount;

    @Override
    public int compareTo(AnswerDTO answer) {
        Long answerVoteCount = this.getVoteCount();
        Long comparedAnswerVoteCount = answer.getVoteCount();

        if (answerVoteCount.equals(comparedAnswerVoteCount)) {
            return 0;
        } else if (answerVoteCount < comparedAnswerVoteCount) {
            return 1;
        } else {
            return -1;
        }
    }
}
