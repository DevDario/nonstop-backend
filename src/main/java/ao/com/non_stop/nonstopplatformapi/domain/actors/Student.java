package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "students")
@AllArgsConstructor
@Builder
public class Student extends User{

    private final Roles role = Roles.STUDENT;
}
