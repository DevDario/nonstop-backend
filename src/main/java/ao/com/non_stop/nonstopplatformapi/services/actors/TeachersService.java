package ao.com.non_stop.nonstopplatformapi.services.actors;

import ao.com.non_stop.nonstopplatformapi.dtos.actors.teachers.TeachersRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeachersService {

    public ResponseEntity<String> registerTeacher(TeachersRequestDTO newTeacher){



        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
