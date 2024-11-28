package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import ao.com.non_stop.nonstopplatformapi.repositories.TeachersRepository;
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
    private final TeachersRepository teacherRepository;

    public Course getCourseById(Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
    }

    public ResponseEntity<String> createCourse(CourseRequestDTO newCourse){

        Teacher teacher = this.teacherRepository.findByEmail(newCourse.teacher_email()).orElseThrow();

        Course course = Course.builder()
                .name(newCourse.name())
                .description(newCourse.description())
                .releaseDate(newCourse.releaseDate())
                .category(newCourse.category())
                .level(newCourse.level())
                .imageUrl(newCourse.imageUrl())
                .duration(newCourse.duration())
                .language(newCourse.language())
                .rating(newCourse.rating())
                .teacher(teacher)
                .build();

        courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<String> deleteCourse(Long courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
        courseRepository.delete(course);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<CourseResponseDTO> getAllCourses(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Course> coursePage = this.courseRepository.findAll(pageable);
        return coursePage.map(course-> new CourseResponseDTO(
                        course.getId(),
            course.getName(),
            course.getDescription(),
            course.getReleaseDate(),
            course.getCategory(),
            course.getLevel(),
            course.getImageUrl(),
                        course.getDuration(),
                        course.getLanguage(),
                        course.getRating(),
                course.getCourseClasses()
                ))
            .stream()
            .toList();
    }

    public Course getCourseByName(String name) throws Exception{
        return this.courseRepository.findByName(name).orElseThrow(()-> new CourseNotFoundException(String.format("There's no %s course",name)));
    }


    public List<CourseResponseDTO> getFilteredCourses(int page, int size, CourseCategory category, CourseLevel level, String language, Float rating){

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

        return coursePage.map(course-> new CourseResponseDTO(
                course.getId(),
                        course.getName(),
                        course.getDescription(),
                        course.getReleaseDate(),
                        course.getCategory(),
                        course.getLevel(),
                        course.getImageUrl(),
                        course.getDuration(),
                        course.getLanguage(),
                        course.getRating(),
                course.getCourseClasses()
                ))
                .stream()
                .toList();
    }

    public ResponseEntity<String> updateCourseDetails(CourseRequestDTO courseDetails, Long courseId)throws CourseNotFoundException{
        Course course = this.courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));

        course.setName(courseDetails.name());
        course.setDescription(courseDetails.description());
        course.setCategory(courseDetails.category());
        course.setLevel(courseDetails.level());
        course.setReleaseDate(courseDetails.releaseDate());
        course.setImageUrl(courseDetails.imageUrl());
        course.setDuration(courseDetails.duration());
        course.setLanguage(courseDetails.language());
        course.setRating(courseDetails.rating());

        this.courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // dev env use only

    public ResponseEntity<String> createMultipleCourses(List<CourseRequestDTO> courses){

        List<Course> newCourses = courses.stream()
                .map(course -> Course.builder()
                        .name(course.name())
                        .description(course.description())
                        .releaseDate(course.releaseDate())
                        .category(course.category())
                        .level(course.level())
                        .imageUrl(course.imageUrl())
                        .duration(course.duration())
                        .language(course.language())
                        .rating(course.rating())
                        .build()
                ).toList();

        this.courseRepository.saveAll(newCourses);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
