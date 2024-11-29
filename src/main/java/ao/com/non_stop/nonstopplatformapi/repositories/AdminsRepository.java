package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends CrudRepository<Admin, String> {
}
