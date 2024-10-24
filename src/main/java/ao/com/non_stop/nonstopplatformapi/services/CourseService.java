package ao.com.non_stop.nonstopplatformapi.services;

import ao.com.non_stop.nonstopplatformapi.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
}
