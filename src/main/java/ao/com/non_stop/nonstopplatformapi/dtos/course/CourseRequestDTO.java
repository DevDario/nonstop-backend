package ao.com.non_stop.nonstopplatformapi.dtos.course;

import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;

import java.util.Date;

public record CourseRequestDTO(String name, String description, Date releaseDate, CourseCategory category, CourseLevel level, String imageUrl, Long duration, String language, Float rating) {
}
