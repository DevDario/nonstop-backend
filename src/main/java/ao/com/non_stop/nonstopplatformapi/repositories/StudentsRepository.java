package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentsRepository extends CrudRepository<Student,String> {
}
