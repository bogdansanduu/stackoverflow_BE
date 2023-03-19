package utcn.stackoverflow.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utcn.stackoverflow.stackoverflow.dto.AuthenticationRequest;
import utcn.stackoverflow.stackoverflow.dto.LoginResponseDto;
import utcn.stackoverflow.stackoverflow.security.config.JwtUtils;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signIn")
    public ResponseEntity<LoginResponseDto> authenticate(
            @RequestBody AuthenticationRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );


        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if (userDetails != null) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(jwtUtils.generateToken(userDetails));

            return ResponseEntity.ok().body(loginResponseDto);
        }

        return ResponseEntity.status(400).body(new LoginResponseDto("ERROR"));
    }
}
