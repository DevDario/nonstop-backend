package ao.com.non_stop.nonstopplatformapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyValueException(NullPointerException e){
        HttpStatus status = HttpStatus.NO_CONTENT;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }


    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCourseNotFoundException(CourseNotFoundException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(RuntimeException ex){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleIncorrectPasswordException(IncorrectPasswordException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex){
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(
                new ExceptionResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );
    }

}
