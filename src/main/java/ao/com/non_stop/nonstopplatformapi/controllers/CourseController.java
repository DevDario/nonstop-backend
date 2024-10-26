package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
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

    @GetMapping("/search/id/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        Course course = courseService.getCourseById(courseId);

        String base64Image = course.getImage() != null ? Base64.getEncoder().encodeToString(course.getImage().getBytes()) : null;

        CourseDTO courseDTO = new CourseDTO(course.getName(), course.getDescription(), course.getReleaseDate(),course.getCategory(),course.getLevel(),base64Image);

        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) throws Exception{

        var course = this.courseService.getCourseByName(name);

        return ResponseEntity.ok(course);
    }

    @GetMapping("/search/filter")
    public ResponseEntity<List<CourseDTO>> filterCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) CourseCategory category,
            @RequestParam(required = false) CourseLevel level
    ){
        List<CourseDTO> courses = this.courseService.getFilteredCourses(page,size,category,level);
        return ResponseEntity.ok(courses);
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
    public ResponseEntity<List<CourseDTO>> getAllCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<CourseDTO> allCourses = this.courseService.getAllCourses(page,size);
        return ResponseEntity.ok(allCourses);
    }
}
