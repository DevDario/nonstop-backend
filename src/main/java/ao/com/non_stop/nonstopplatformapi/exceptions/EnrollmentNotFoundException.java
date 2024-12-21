package ao.com.non_stop.nonstopplatformapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(String message){
        super(message);
    }
}