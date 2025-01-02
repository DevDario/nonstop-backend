package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.admin.AdminsRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.actors.admin.AdminsResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.services.AdminService;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final CourseService courseService;
    private final AdminService adminService;

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

    @GetMapping("/profile/")
    public ResponseEntity<AdminsResponseDTO> getProfileDetails(Principal principal){
        String email = principal.getName();
        return this.adminService.getProfileDetails(email);
    }

    @PutMapping("/profile/")
    public ResponseEntity<String> updateProfileDetails(Principal principal, @RequestBody AdminsRequestDTO body){
        String email = principal.getName();
        return this.adminService.updateDetails(email, body);
    }
}