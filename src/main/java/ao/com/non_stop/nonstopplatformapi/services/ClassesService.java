package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.domain.entities.CourseClasses;
import ao.com.non_stop.nonstopplatformapi.dtos.classes.NewClassRequestDTO;
import ao.com.non_stop.nonstopplatformapi.repositories.ClassesRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.TeachersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClassesService {

    private final TeachersRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final ClassesRepository classesRepository;

    public ResponseEntity<String> addClassToCourse(String teacher_email, long course_id, NewClassRequestDTO newContent) {
        Teacher currentTeacher = this.teacherRepository.findByEmail(teacher_email).orElseThrow(() -> new UsernameNotFoundException("There is no Teacher With This Email !"));
        Course course = this.courseRepository.findByIdAndTeacher(course_id, currentTeacher);

        CourseClasses newCourseContent = CourseClasses.builder()
                .title(newContent.title())
                .content_url(newContent.content_url())
                .duration(newContent.duration())
                .content_order(newContent.content_order())
                .course(course)
                .is_completed(false)
                .build();

        this.classesRepository.save(newCourseContent);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
