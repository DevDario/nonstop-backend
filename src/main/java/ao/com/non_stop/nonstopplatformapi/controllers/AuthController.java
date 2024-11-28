package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import ao.com.non_stop.nonstopplatformapi.dtos.LoginRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.RegisterRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.ResponseDTO;
import ao.com.non_stop.nonstopplatformapi.infra.security.TokenService;
import ao.com.non_stop.nonstopplatformapi.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private TokenService tokenService;
    private AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerDTO) throws Exception{
        User authenticatedUser = this.authService.signup(registerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(authenticatedUser.getName(), null));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) throws Exception{
        User newUser = authService.login(loginDTO);
        String token = this.tokenService.generateToken(newUser);

        return ResponseEntity.ok().body(new ResponseDTO(newUser.getEmail(), token));
    }
}
