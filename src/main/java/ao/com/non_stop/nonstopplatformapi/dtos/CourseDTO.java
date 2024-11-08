package ao.com.non_stop.nonstopplatformapi.dtos;

import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;

import java.util.Date;

public record CourseDTO(String name, String description, Date releaseDate, CourseCategory category, CourseLevel level, String image) {
}
