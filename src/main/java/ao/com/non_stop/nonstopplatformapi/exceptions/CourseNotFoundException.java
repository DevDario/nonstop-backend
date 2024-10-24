package ao.com.non_stop.nonstopplatformapi.exceptions;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(Long id){
        super(String.format("There's no course with id of -> %d", id));
    }
}

