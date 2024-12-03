package ao.com.non_stop.nonstopplatformapi.controllers;

import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    CourseService courseService;

    @GetMapping("/")
    public List<CourseResponseDTO> getAllCoursesFromPlatform(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "15") int size){
        return this.courseService.getAllCourses(page, size);
    }
}
