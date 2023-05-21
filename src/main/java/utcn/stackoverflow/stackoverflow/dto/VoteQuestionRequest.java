package utcn.stackoverflow.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VoteQuestionRequest {
    private final Long userId;
    private final Long questionId;
    private final int value;
}
