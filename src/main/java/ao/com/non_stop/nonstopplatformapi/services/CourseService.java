package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourseById(Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public Course createCourse(CourseDTO courseDTO){
        Course course = Course.builder()
                .name(courseDTO.name())
                .description(courseDTO.description())
                .releaseDate(courseDTO.releaseDate())
                .image(courseDTO.image())
                .build();

        courseRepository.save(course);

        return course;
    }

    public List<Course> deleteCourse(Long courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException(courseId));
        courseRepository.delete(course);

        return courseRepository.findAll();
    }
}
