package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Admin;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.admin.AdminsRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.admin.AdminsResponseDTO;
import ao.com.non_stop.nonstopplatformapi.exceptions.EmailAlreadyInUseException;
import ao.com.non_stop.nonstopplatformapi.exceptions.UserNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.AdminsRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminsRepository adminsRepository;
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> updateDetails(String email, AdminsRequestDTO details){
        try {
            Admin adm = this.adminsRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No Admin Were Found !"));

            if (userRepository.findByEmail(details.email()).isPresent()) {
                throw new EmailAlreadyInUseException("Email is already in use. Try to Another");
            }else{
                adm.setName(details.name());
                adm.setEmail(details.email() != null ? details.email() : adm.getEmail());
                adm.setPassword(passwordEncoder.encode(details.password()));

                this.adminsRepository.save(adm);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("There's no Admin with ["+email+"] Email !");
        }
    }

    public ResponseEntity<AdminsResponseDTO> getProfileDetails(String email){
        Admin adm = this.adminsRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("No Admin Were Found !"));
        return ResponseEntity.status(HttpStatus.OK).body(
                new AdminsResponseDTO(null,
                        adm.getName(),
                        adm.getEmail(),
                        adm.getCreated_at(),
                        adm.getUpdated_at()
        ));
    }
}