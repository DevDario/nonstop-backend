package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Admin;
import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import ao.com.non_stop.nonstopplatformapi.dtos.auth.LoginRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.auth.RegisterRequestDTO;
import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import ao.com.non_stop.nonstopplatformapi.exceptions.EmailAlreadyInUseException;
import ao.com.non_stop.nonstopplatformapi.exceptions.IncorrectPasswordException;
import ao.com.non_stop.nonstopplatformapi.exceptions.UserNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterRequestDTO registerDTO){

        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use. Try to Login");
        }

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

                Teacher teacher = (Teacher) newUser;
                teacher.setAbout_me(registerDTO.about_me());
                teacher.setSpecialization(registerDTO.specialization());
                teacher.setCourses(List.of());

                break;

            case Roles.STUDENT:
                newUser = new Student();
                newUser.setName(registerDTO.name());
                newUser.setEmail(registerDTO.email());
                newUser.setRole(registerDTO.role());
                newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
                break;

            default:
                throw new RuntimeException("Invalid Role specified!");
        }
        userRepository.save(newUser);
        return newUser;
    }

    public User login(LoginRequestDTO loginDTO) throws Exception{

        User user = this.userRepository
                .findByEmail(
                        loginDTO.email()
                ).orElseThrow(()-> new UserNotFoundException("We couldn't find a user with this email"));

        if(passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            try{
                authenticationManager
                        .authenticate
                                (new UsernamePasswordAuthenticationToken(
                                        loginDTO.email(),
                                        loginDTO.password()));
            }catch(Exception e){
                throw new RuntimeException("Error While Authenticating User ! " + e);
            }

            return user;
        }
        throw new IncorrectPasswordException("Wrong Password. Try Again !");
    }
}
