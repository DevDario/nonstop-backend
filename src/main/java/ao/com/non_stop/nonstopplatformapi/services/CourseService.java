package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourseById(Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public Course createCourse(CourseDTO courseDTO){
        Course course = Course.builder()
                .name(courseDTO.name())
                .description(courseDTO.description())
                .releaseDate(courseDTO.releaseDate())
                .category(courseDTO.category())
                .level(courseDTO.level())
                .image(courseDTO.image())
                .build();

        courseRepository.save(course);

        return course;
    }

    public List<Course> deleteCourse(Long courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException(courseId));
        courseRepository.delete(course);

        return this.courseRepository.findAll();
    }

    public List<CourseDTO> getAllCourses(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Course> coursePage = this.courseRepository.findAll(pageable);
        return coursePage.map(course-> new CourseDTO(course.getName(), course.getDescription(), course.getReleaseDate(),course.getCategory(),course.getLevel(),course.getImage())).stream().toList();
    }

    public Course getCourseByName(String name) throws Exception{
        return this.courseRepository.findByName(name).orElseThrow(()-> new Exception("This Course Doesn't Exists !"));
    }


    public List<CourseDTO> getFilteredCourses(int page, int size, CourseCategory category, CourseLevel level){

        category = (category!=null) ? category:CourseCategory.WEB;
        level = (level!=null) ? level:CourseLevel.BEGINNER;

        Pageable pageable = PageRequest.of(page,size);

        Page<Course> coursePage = this.courseRepository.findFilteredCourses(
                category,
                level,
                pageable);

        return coursePage.map(course-> new CourseDTO(
                        course.getName(),
                        course.getDescription(),
                        course.getReleaseDate(),
                        course.getCategory(),
                        course.getLevel(),
                        course.getImage()))
                .stream()
                .toList();
    }
}
