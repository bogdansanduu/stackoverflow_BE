package utcn.stackoverflow.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import utcn.stackoverflow.stackoverflow.entity.Tag;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TagRequest {
    private  final Tag tag;
    private final Long questionId;

}
