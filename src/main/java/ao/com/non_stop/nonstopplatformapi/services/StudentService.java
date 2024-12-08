package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Enrollment;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.students.StudentsProfileResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.repositories.EnrollmentRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.StudentsRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentsRepository studentsRepository;
    private final UsersRepository usersRepository;
    private final EnrollmentRepository enrollmentRepository;
    //private final Authentication authentication;


    @Transactional
    public ResponseEntity<String> deleteAccount(String email) throws UsernameNotFoundException{
        try{
            Student student = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
            this.studentsRepository.delete(student);
            //this.authentication.setAuthenticated(false);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("There's no Student with ["+email+"]email !");
        }
    }

    public ResponseEntity<List<CourseOverviewResponseDTO>> getAllEnrollments(String email){
        Student student = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
        List<Enrollment> enrollments = this.enrollmentRepository.findByStudent(student);

        return ResponseEntity.status(HttpStatus.OK).body(enrollments.stream().map(enrollment -> new CourseOverviewResponseDTO(
                enrollment.getCourse().getId(),
                enrollment.getCourse().getName(),
                enrollment.getCourse().getDescription(),
                enrollment.getCourse().getReleaseDate(),
                enrollment.getCourse().getCategory(),
                enrollment.getCourse().getLevel(),
                enrollment.getCourse().getImageUrl(),
                enrollment.getCourse().getDuration(),
                enrollment.getCourse().getLanguage(),
                enrollment.getCourse().getRating()
        )).toList());
    }

    public ResponseEntity<StudentsProfileResponseDTO> getProfileDetails(String email){
        Student student = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
        return ResponseEntity.status(HttpStatus.OK).body(
                new StudentsProfileResponseDTO(
                        student.getName(),
                        student.getEmail(),
                        student.getCreated_at(),
                        this.getAllEnrollments(email)
                )
        );
    }
}
