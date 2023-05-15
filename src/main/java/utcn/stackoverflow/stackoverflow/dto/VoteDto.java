package utcn.stackoverflow.stackoverflow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VoteDto {
    private Long userId;
    private Long creatorId;
    private int value;

    public VoteDto(Long userId, Long creatorId, int value) {
        this.userId = userId;
        this.creatorId = creatorId;
        this.value = value;
    }
}
