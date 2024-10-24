package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        Course course = courseService.getCourseById(courseId);

        String base64Image = course.getImage() != null ? Base64.getEncoder().encodeToString(course.getImage().getBytes()) : null;

        CourseDTO courseDTO = new CourseDTO(course.getName(), course.getDescription(), course.getReleaseDate(),base64Image);

        return ResponseEntity.ok(courseDTO);
    }

    @PostMapping("/new")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO body){
        Course newCourse = this.courseService.createCourse(body);
        return ResponseEntity.ok(newCourse);
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<List<Course>> deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException{
        List<Course> courses = this.courseService.deleteCourse(courseId);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses(){
        var courses = this.courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
}
