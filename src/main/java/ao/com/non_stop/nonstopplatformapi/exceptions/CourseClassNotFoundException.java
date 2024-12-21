package ao.com.non_stop.nonstopplatformapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseClassNotFoundException extends RuntimeException {
    public CourseClassNotFoundException(String message){
        super(message);
    }
}