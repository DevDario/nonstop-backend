package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    CourseService courseService;

    @DeleteMapping("/{course_id}")
    public ResponseEntity<String> deleteCourseFromPlatform(@PathVariable(name = "course_id") long course_id){
        return this.courseService.deleteCourse(course_id);
    }

    @GetMapping("/")
    public List<CourseResponseDTO> getAllCoursesFromPlatform(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "15") int size){
        return this.courseService.getAllCourses(page, size);
    }

    @GetMapping("/{course_id}")
    public Course getSingleCourseFromPlatform(@PathVariable(name = "course_id") long course_id){
        return this.courseService.getCourseById(course_id);
    }
}
