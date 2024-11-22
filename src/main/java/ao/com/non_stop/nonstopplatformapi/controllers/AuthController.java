package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Admin;
import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import ao.com.non_stop.nonstopplatformapi.dtos.LoginRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.RegisterRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.ResponseDTO;
import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import ao.com.non_stop.nonstopplatformapi.infra.security.TokenService;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UsersRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerDTO) {

        // Check if the email is already taken
        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Determine the role and create the corresponding user
        User newUser;
        switch (registerDTO.role()) {
            case Roles.ADMIN:
                newUser = new Admin();
                newUser.setName(registerDTO.name());
                newUser.setEmail(registerDTO.email());
                newUser.setRole(registerDTO.role());
                newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
                break;

            case Roles.TEACHER:
                newUser = new Teacher();
                newUser.setName(registerDTO.name());
                newUser.setEmail(registerDTO.email());
                newUser.setRole(registerDTO.role());
                newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
                break;

            case Roles.STUDENT:
                newUser = new Student();
                newUser.setName(registerDTO.name());
                newUser.setEmail(registerDTO.email());
                newUser.setRole(registerDTO.role());
                newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
                break;

            default:
                return ResponseEntity.badRequest().body("Error: Invalid role specified!");
        }

        // Save the user to the database
        String token = this.tokenService.generateToken(newUser,registerDTO.role().name());
        userRepository.save(newUser);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginDTO){
        User user = this.userRepository.findByEmail(loginDTO.email()).orElseThrow(()-> new RuntimeException("User not found with email " + loginDTO.email()));
        if(passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user,user.getRole().name());
            return ResponseEntity.ok().body( new ResponseDTO(user.getName(),token));
        }
        return ResponseEntity.badRequest().build();
    }
}
