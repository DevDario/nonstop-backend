package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.actors.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User,String> {
    Optional<User> findByEmail(String login);
}
