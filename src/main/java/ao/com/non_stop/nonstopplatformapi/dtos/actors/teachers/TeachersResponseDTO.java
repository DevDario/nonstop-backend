package ao.com.non_stop.nonstopplatformapi.dtos.actors.teachers;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;

import java.util.List;

public record TeachersResponseDTO(String id, String name, String email, String about, String specialization, List<Course> created_courses) {
}
