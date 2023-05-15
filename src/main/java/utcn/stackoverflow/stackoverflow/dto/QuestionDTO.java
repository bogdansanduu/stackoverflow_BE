package utcn.stackoverflow.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utcn.stackoverflow.stackoverflow.entity.Content;
import utcn.stackoverflow.stackoverflow.entity.Tag;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class QuestionDTO implements Comparable<QuestionDTO> {

    private Long id;
    private String title;
    private Content content;
    private Set<Tag> tags;
    private UserDTO creator;

    private Long voteCount;

    @Override
    public int compareTo(QuestionDTO questionDTO) {
        LocalDateTime questionDate = this.content.getCreatedAt();
        LocalDateTime comparedQuestionDate = questionDTO.getContent().getCreatedAt();

        if (questionDate.isEqual(comparedQuestionDate)) {
            return 0;
        } else if (questionDate.isBefore(comparedQuestionDate)) {
            return 1;
        } else {
            return -1;
        }
    }
}
