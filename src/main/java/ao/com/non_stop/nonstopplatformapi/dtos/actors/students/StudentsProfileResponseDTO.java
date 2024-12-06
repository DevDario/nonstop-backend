package ao.com.non_stop.nonstopplatformapi.dtos.actors.students;

import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public record StudentsProfileResponseDTO(String name, String email, String Created_At, ResponseEntity<List<CourseOverviewResponseDTO>> Enrolled_Courses) {
}
