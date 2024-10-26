package ao.com.non_stop.nonstopplatformapi.repositories;

import ao.com.non_stop.nonstopplatformapi.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByName(String name);

    @Query("SELECT c FROM Course c " +
            "WHERE (:category = '' OR c.category = :category) " +
            "AND (:level = '' OR c.level = :level) "
    )
    Page<Course> findFilteredCourses(
            @Param("category") CourseCategory category,
            @Param("level")CourseLevel level,
            Pageable pageable
            );
}
