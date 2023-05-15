package utcn.stackoverflow.stackoverflow.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetVoteRequest {
    private final Long userId;
    private final Long creatorId;
}
