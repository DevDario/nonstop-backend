package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.actors.teachers.TeachersResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.classes.NewClassRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.services.ClassesService;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import ao.com.non_stop.nonstopplatformapi.services.TeachersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    private final CourseService courseService;
    private final ClassesService classesService;
    private final TeachersService teachersService;

    @PostMapping("/courses/")
    public ResponseEntity<String> createCourse(@RequestBody CourseRequestDTO body) throws Exception{
        return this.courseService.createCourse(body);
    }

    @Transactional
    @DeleteMapping("/courses/{course_id}")
    public ResponseEntity<String> deleteCourse(Principal principal, @PathVariable long course_id) throws Exception{
        String email = this.getTeacherEmail(principal);
        return this.courseService.deleteCourseFromTeacher(email,course_id);
    }

    @PostMapping("/courses/{course_id}")
    public ResponseEntity<String> addNewContentToCourse(Principal principal, @PathVariable long course_id, @RequestBody NewClassRequestDTO body){
        String email = this.getTeacherEmail(principal);
        return this.classesService.addClassToCourse(email,course_id,body);
    }

    @Transactional
    @DeleteMapping("/courses/{course_id}/{class_id}")
    public ResponseEntity<String> deleteContenFromCourse(Principal principal, @PathVariable long course_id, @PathVariable long class_id){
        String email = this.getTeacherEmail(principal);
        return this.classesService.removeClassFromCourse(email,course_id,class_id);
    }


    @GetMapping("/profile/")
    public ResponseEntity<TeachersResponseDTO> getProfileDetails(Principal principal){
        String email = this.getTeacherEmail(principal);
        return this.teachersService.getProfileDetails(email);
    }

    @GetMapping("/courses/")
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
