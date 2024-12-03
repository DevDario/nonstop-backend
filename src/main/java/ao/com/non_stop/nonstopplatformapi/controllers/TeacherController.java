package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    CourseService courseService;

    @PostMapping("/")
    public ResponseEntity<String> createCourse(@RequestBody CourseRequestDTO body) throws Exception{
        return this.courseService.createCourse(body);
    }

    @DeleteMapping("/{course_id}")
    public ResponseEntity<String> deleteCourse(Principal principal, @PathVariable long course_id) throws Exception{
        String email = this.getTeacherEmail(principal);
        return this.courseService.deleteCourseFromTeacher(email,course_id);
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseOverviewResponseDTO>> getAllCourses(Principal principal,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size) throws  Exception{
        String email = this.getTeacherEmail(principal);
        List<CourseOverviewResponseDTO> allCourses = this.courseService.getAllCoursesFromTeacher(page,size,email);
        return ResponseEntity.ok(allCourses);
    }

    private String getTeacherEmail(Principal principal){
        return principal.getName();
    }
}
