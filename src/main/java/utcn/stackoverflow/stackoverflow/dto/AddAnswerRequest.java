package utcn.stackoverflow.stackoverflow.dto;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddAnswerRequest {
    private final Long questionId;
    private final Long userId;
    private final String description;
    private final String picture;
}
