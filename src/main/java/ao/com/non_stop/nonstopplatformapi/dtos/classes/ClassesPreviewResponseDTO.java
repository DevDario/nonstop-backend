package ao.com.non_stop.nonstopplatformapi.dtos.classes;

import ao.com.non_stop.nonstopplatformapi.domain.entities.CourseClasses;

import java.util.List;

public record ClassesPreviewResponseDTO(boolean isEnrolled, List<CourseClasses> classesList) {
}
