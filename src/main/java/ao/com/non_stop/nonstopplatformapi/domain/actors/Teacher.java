package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;
import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher extends User{

    @Size(max = 110, message = "About Me cannot exceed 110 characters.")
    @Column(name = "about_me", nullable = false)
    private String about_me;

    @Size(max = 255, message = "Specialization cannot exceed 255 characters.")
    @Column(name = "specialization", nullable = false)
    private String specialization;

    private final Roles role = Roles.TEACHER;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Course> created_courses;
}
