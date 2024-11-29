package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> deleteCourse(@AuthenticationPrincipal Authentication principal, @PathVariable long course_id) throws Exception{
        String email = principal.getName();
        return this.courseService.deleteCourseByTeacher(email,course_id);
    }
}
