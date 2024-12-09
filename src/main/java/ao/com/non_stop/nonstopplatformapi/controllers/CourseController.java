package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        Course course = courseService.getCourseById(courseId);

        CourseResponseDTO response = new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getReleaseDate(),
                course.getCategory(),
                course.getLevel(),
                course.getImageUrl(),
                course.getDuration(),
                course.getLanguage(),
                course.getRating(),
                course.getCourseClasses());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) throws Exception{

        var course = this.courseService.getCourseByName(name);

        return ResponseEntity.ok(course);
    }

    @GetMapping("/filter/")
    public ResponseEntity<List<CourseResponseDTO>> filterCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) CourseCategory category,
            @RequestParam(required = false) CourseLevel level,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Float rating
    ){
        List<CourseResponseDTO> courses = this.courseService.getFilteredCourses(page,size,category,level, language, rating);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/")
    public ResponseEntity<String> createCourse(@RequestBody CourseRequestDTO body) throws Exception{
        return this.courseService.createCourse(body);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException{
        return this.courseService.deleteCourse(courseId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<CourseResponseDTO> allCourses = this.courseService.getAllCourses(page,size);
        return ResponseEntity.ok(allCourses);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourseDetails(@RequestBody CourseRequestDTO body, @PathVariable Long courseId) throws CourseNotFoundException{
        return this.courseService.updateCourseDetails(body,courseId);
    }

    @PostMapping("/enroll/{course_id}")
    public ResponseEntity<String> enrollInCourse(Principal principal, @PathVariable(name = "course_id") Long course_id){
        String email = principal.getName();
        return this.courseService.enrollInCourse(email,course_id);
    }

    /**
     * @return all registered categories,
     * languages or levels from courses,
     * so the user can filter courses
     * by those parameters
     *
     * @param param
     * the parameter to look for (category, level or language)
     *
     * @throws IllegalArgumentException if no param valid value is provided
     **/

    @GetMapping("/topics")
    public ResponseEntity<List<String>> getTopics(@RequestParam(name = "param") String param){
        var topicsList = this.courseService.getAllTopics(param);
        return ResponseEntity.status(HttpStatus.OK).body(topicsList);
    }

    //dev env use only
    @PostMapping("/list/")
    public ResponseEntity<String> createMultipleCourse(@RequestBody List<CourseRequestDTO> body){
        return this.courseService.createMultipleCourses(body);
    }
}
