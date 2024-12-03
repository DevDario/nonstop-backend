package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import ao.com.non_stop.nonstopplatformapi.repositories.StudentsRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentsRepository studentsRepository;
    private UsersRepository usersRepository;


    @Transactional
    public ResponseEntity<String> deleteAccount(String email){
        try{
            Student student = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
            this.studentsRepository.delete(student);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("There's no Student with ["+email+"]email !");
        }
    }
}
