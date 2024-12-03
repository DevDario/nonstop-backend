package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
}
