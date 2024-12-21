package ao.com.non_stop.nonstopplatformapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}