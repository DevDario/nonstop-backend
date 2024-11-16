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
@RequestMapping("/api/v1/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        Course course = courseService.getCourseById(courseId);

        String base64Image = course.getImage() != null ? Base64.getEncoder().encodeToString(course.getImage().getBytes()) : null;

        CourseDTO courseDTO = new CourseDTO(
                course.getName(),
                course.getDescription(),
                course.getReleaseDate(),
                course.getCategory(),
                course.getLevel(),
                base64Image,
                course.getDuration(),
                course.getLanguage(),
                course.getRating());

        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) throws Exception{

        var course = this.courseService.getCourseByName(name);

        return ResponseEntity.ok(course);
    }

    @GetMapping("/filter/")
    public ResponseEntity<List<CourseDTO>> filterCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) CourseCategory category,
            @RequestParam(required = false) CourseLevel level,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Float rating
    ){
        List<CourseDTO> courses = this.courseService.getFilteredCourses(page,size,category,level, language, rating);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO body){
        return this.courseService.createCourse(body);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException{
        return this.courseService.deleteCourse(courseId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<CourseDTO> allCourses = this.courseService.getAllCourses(page,size);
        return ResponseEntity.ok(allCourses);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourseDetails(@RequestBody CourseDTO body, @PathVariable Long courseId) throws CourseNotFoundException{
        return this.courseService.updateCourseDetails(body,courseId);
    }
}
