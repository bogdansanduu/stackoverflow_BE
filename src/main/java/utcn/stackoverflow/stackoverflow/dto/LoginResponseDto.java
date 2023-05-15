package utcn.stackoverflow.stackoverflow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private String token;
    private UserDTO user;

    public LoginResponseDto(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}
