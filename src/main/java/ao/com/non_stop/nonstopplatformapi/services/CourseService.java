package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.dtos.CourseDTO;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourseById(Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
    }

    public ResponseEntity<String> createCourse(CourseDTO courseDTO){
        Course course = Course.builder()
                .name(courseDTO.name())
                .description(courseDTO.description())
                .releaseDate(courseDTO.releaseDate())
                .category(courseDTO.category())
                .level(courseDTO.level())
                .image(courseDTO.image())
                .duration(courseDTO.duration())
                .language(courseDTO.language())
                .rating(courseDTO.rating())
                .build();

        courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<String> deleteCourse(Long courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
        courseRepository.delete(course);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<CourseDTO> getAllCourses(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Course> coursePage = this.courseRepository.findAll(pageable);
        return coursePage.map(course-> new CourseDTO(
            course.getName(),
            course.getDescription(),
            course.getReleaseDate(),
            course.getCategory(),
            course.getLevel(),
            course.getImage(),
                        course.getDuration(),
                        course.getLanguage(),
                        course.getRating()
                ))
            .stream()
            .toList();
    }

    public Course getCourseByName(String name) throws Exception{
        return this.courseRepository.findByName(name).orElseThrow(()-> new CourseNotFoundException(String.format("There's no %s course",name)));
    }


    public List<CourseDTO> getFilteredCourses(int page, int size, CourseCategory category, CourseLevel level, String language, Float rating){

        category = (category!=null) ? category:CourseCategory.WEB;
        level = (level!=null) ? level:CourseLevel.BEGINNER;
        language = (language!=null) ? language:"english";
        rating = (rating!=null) ? rating:3.0F;

        Pageable pageable = PageRequest.of(page,size);

        Page<Course> coursePage = this.courseRepository.findFilteredCourses(
                category,
                level,
                language,
                rating,
                pageable);

        return coursePage.map(course-> new CourseDTO(
                        course.getName(),
                        course.getDescription(),
                        course.getReleaseDate(),
                        course.getCategory(),
                        course.getLevel(),
                        course.getImage(),
                        course.getDuration(),
                        course.getLanguage(),
                        course.getRating()
                ))
                .stream()
                .toList();
    }

    public ResponseEntity<String> updateCourseDetails(CourseDTO courseDetails, Long courseId)throws CourseNotFoundException{
        Course course = this.courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));

        course.setName(courseDetails.name());
        course.setDescription(courseDetails.description());
        course.setCategory(courseDetails.category());
        course.setLevel(courseDetails.level());
        course.setReleaseDate(courseDetails.releaseDate());
        course.setImage(courseDetails.image());
        course.setDuration(courseDetails.duration());
        course.setLanguage(courseDetails.language());
        course.setRating(courseDetails.rating());

        this.courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
