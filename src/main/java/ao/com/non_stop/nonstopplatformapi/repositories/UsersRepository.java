package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User,String> {
}
