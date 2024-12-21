package ao.com.non_stop.nonstopplatformapi.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ExceptionResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date timestamp;
    private String message;
    private String status;
    private int code;
    private String stackTrace;
    private Object data;

    public ExceptionResponse(){
        this.timestamp = new Date();
    }

    public ExceptionResponse(
            HttpStatus httpStatus,
            String message
    ){
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ExceptionResponse(
            HttpStatus httpStatus,
            String message,
            String stackTrace
    ){
        this(
                httpStatus,
                message
        );
        this.stackTrace = stackTrace;
    }

    public ExceptionResponse(
            HttpStatus httpStatus,
            String message,
            String stackTrace,
            Object data
    ){
        this(
                httpStatus,
                message,
                stackTrace
        );
        this.data = data;
    }


}
