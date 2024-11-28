package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeachersRepository extends CrudRepository<Teacher,String> {
    Optional<Teacher> findByEmail(String email);
}
