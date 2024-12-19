package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import ao.com.non_stop.nonstopplatformapi.domain.entities.CourseClasses;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Enrollment;
import ao.com.non_stop.nonstopplatformapi.dtos.classes.ClassesPreviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.classes.ProgressRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseOverviewResponseDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseRequestDTO;
import ao.com.non_stop.nonstopplatformapi.dtos.course.CourseResponseDTO;
import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import ao.com.non_stop.nonstopplatformapi.exceptions.CourseNotFoundException;
import ao.com.non_stop.nonstopplatformapi.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeachersRepository teacherRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UsersRepository usersRepository;
    private final ClassesRepository classesRepository;

    // acessible for all ROLES
    public Course getCourseById(Long courseId) throws CourseNotFoundException{
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
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

    public Course getCourseByName(String name){
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

    public List<String> getAllTopics(String parameter) throws IllegalArgumentException{
        return switch (parameter) {
            case "byCategory" -> this.courseRepository.findCategories().stream().map(Enum::name).toList();
            case "byLanguage" -> this.courseRepository.findLanguages().stream().toList();
            case "byLevel" -> this.courseRepository.findLevels().stream().map(Enum::name).toList();
            case null, default -> throw new IllegalArgumentException("No Valid Parameter was Provided !");
        };
    }

    // only for Users with ADMIN Role
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

    public ResponseEntity<String> deleteCourse(Long courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("We couldn't find a course with the ID of " + courseId));
        courseRepository.delete(course);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Also for Users with TEACHER Role
    public ResponseEntity<String> createCourse(CourseRequestDTO newCourse) throws Exception{

        Teacher teacher = this.teacherRepository.findByEmail(newCourse.teacher_email()).orElseThrow(()-> new Exception("This Email Does not Belongs to Any Teacher !"));

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


    // Teacher-Entity-Focused methds
    public ResponseEntity<String> deleteCourseFromTeacher(String teacher_email, long course_id){
        Teacher currentTeacher = this.teacherRepository.findByEmail(teacher_email).orElseThrow(()-> new UsernameNotFoundException("There is no Teacher With This Email !"));
        Course courseToDelete = this.courseRepository.findByIdAndTeacher(course_id,currentTeacher);
        this.courseRepository.delete(courseToDelete);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<CourseOverviewResponseDTO> getAllCoursesFromTeacher(int size, int page,String email) throws  Exception{
        Teacher currentTeacher = this.teacherRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("There is no Teacher With This Email !"));

        Pageable pageable = PageRequest.of(page,size);
        Page<Course> coursePage = this.courseRepository.findByTeacher(currentTeacher,pageable);
        return coursePage.map(course-> new CourseOverviewResponseDTO(
                        course.getId(),
                        course.getName(),
                        course.getDescription(),
                        course.getReleaseDate(),
                        course.getCategory(),
                        course.getLevel(),
                        course.getImageUrl(),
                        course.getDuration(),
                        course.getLanguage(),
                        course.getRating()
                ))
                .stream()
                .toList();
    }

    // Student-Entity-Focused methods
    public ResponseEntity<String> enrollInCourse(String email, Long course_id){
        Student student = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
        Course course = this.courseRepository.findById(course_id).orElseThrow(()-> new CourseNotFoundException("Course Not Found !"));

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .is_completed(false)
                .progress(0F)
                .build();

        this.enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ClassesPreviewResponseDTO getAllClassesFromCourse(String email, long course_id) throws Exception{
        Course selectedCourse = this.courseRepository.findById(course_id).orElseThrow(()-> new CourseNotFoundException("Course Not Found !"));
        Student loggedStudent = (Student) this.usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No Student Were Found !"));
        Optional<Enrollment> enrollment = this.enrollmentRepository.findByStudentAndCourse(loggedStudent,selectedCourse);

        if(enrollment.isPresent()){
            var classes = enrollment.get().getCourse().getCourseClasses();
            return new ClassesPreviewResponseDTO(true, classes);
        }
        return new ClassesPreviewResponseDTO(false,null);
    }

    public CourseClasses getClassFromCourse(long course_id, long class_id){
        Course course = this.courseRepository.findById(course_id).orElseThrow(()-> new CourseNotFoundException("Course Not Found !"));
        return this.classesRepository.findByCourseAndId(course,class_id);
    }


    public ResponseEntity<String> updateProgress(String email, ProgressRequestDTO details) throws Exception{

        try {
            Student loggedStudent = (Student) this.usersRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No student were Found !"));
            Course selectedCourse = this.courseRepository.findById(details.course_id()).orElseThrow(() -> new CourseNotFoundException("Course Not Found !"));
            Optional<Enrollment> enrollment = this.enrollmentRepository.findByStudentAndCourse(loggedStudent,selectedCourse);

            if(enrollment.isPresent()){
                CourseClasses currentClass = this.classesRepository.findByCourseAndId(selectedCourse, details.class_id());
                currentClass.setIs_completed(details.is_completed());
                this.classesRepository.save(currentClass);
            }else{
                throw new Exception("You're not Enrolled on this Course !");
            }

        }catch(Exception e){
            throw new Exception("Neither Course nor Class Were Found ! \n " + e.getMessage());
        }

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
