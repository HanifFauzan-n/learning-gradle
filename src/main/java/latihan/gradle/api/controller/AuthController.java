package latihan.gradle.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import latihan.gradle.api.dto.AuthRequest;
import latihan.gradle.api.dto.AuthResponse;
import latihan.gradle.api.security.JwtUtil;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            final String jwt = jwtUtil.generateToken(userDetails);

            String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

            return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), role));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Username atau Password salah");
        }

    }
}
