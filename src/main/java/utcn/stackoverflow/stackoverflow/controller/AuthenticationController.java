package utcn.stackoverflow.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import utcn.stackoverflow.stackoverflow.dto.AuthenticationRequest;
import utcn.stackoverflow.stackoverflow.dto.LoginResponseDto;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.security.config.JwtUtils;
import utcn.stackoverflow.stackoverflow.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    private final UserService userService;

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
            UserDTO userDTO = userService.findUserByEmail(request.getEmail());

            LoginResponseDto loginResponseDto = new LoginResponseDto(jwtUtils.generateToken(userDetails), userDTO);

            return ResponseEntity.ok().body(loginResponseDto);
        }

        return ResponseEntity.status(400).body(new LoginResponseDto("ERROR", null));
    }

    @GetMapping("/some-endpoint")
    public ResponseEntity<?> someEndpoint(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println(role);

        return null;
    }
}
