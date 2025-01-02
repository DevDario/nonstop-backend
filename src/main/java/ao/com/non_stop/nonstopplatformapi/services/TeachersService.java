package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.teachers.TeachersRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.teachers.TeachersResponseDTO;
import ao.com.non_stop.nonstopplatformapi.exceptions.UserNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.TeachersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TeachersService {

    private final TeachersRepository teachersRepository;

    public ResponseEntity<String> updateDetails(String email,TeachersRequestDTO details){
        try {
            Teacher teacher = this.teachersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No Teacher Were Found !"));
            teacher.setSpecialization(details.specialization());
            teacher.setAbout_me(details.about());
            teacher.setName(details.name());

            teachersRepository.save(teacher);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("There's no Teacher with ["+email+"] email !");
        }
    }

    @Transactional
    public ResponseEntity<String> deleteAccount(String email){
        try{
            Teacher teacher = this.teachersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No Teacher Were Found !"));
            this.teachersRepository.delete(teacher);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("There's no Teacher with ["+email+"] email !");
        }
    }

    public ResponseEntity<TeachersResponseDTO> getProfileDetails(String email){
        Teacher teacher = this.teachersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No Teacher Were Found !"));
        return ResponseEntity.status(HttpStatus.OK).body(
                new TeachersResponseDTO(null,
                teacher.getName(),
                teacher.getEmail(),
                teacher.getAbout_me(),
                teacher.getSpecialization(),
                teacher.getCourses())
        );
    }
}