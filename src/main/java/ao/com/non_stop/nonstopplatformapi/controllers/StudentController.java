package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.actors.students.StudentsProfileResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import ao.com.non_stop.nonstopplatformapi.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/")
    public List<CourseResponseDTO> getAllCoursesFromPlatform(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "15") int size){
        return this.courseService.getAllCourses(page, size);
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<CourseOverviewResponseDTO>> getAllEnrolledCourses(Principal principal){
        String email = this.getStudentEmail(principal);
        return this.studentService.getAllEnrollments(email);
    }

    @GetMapping("/profile/")
    public ResponseEntity<StudentsProfileResponseDTO> getProfileDetails(Principal principal){
        String email =this.getStudentEmail(principal);
        return this.studentService.getProfileDetails(email);
    }

    private String getStudentEmail(Principal principal){
        return principal.getName();
    }
}
