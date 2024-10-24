package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourseById(@PathVariable Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }
}
