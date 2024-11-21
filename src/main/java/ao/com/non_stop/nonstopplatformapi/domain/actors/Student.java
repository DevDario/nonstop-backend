package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "students")
@Builder
public class Student extends User{

    public Student(){
        this.setRole(Roles.STUDENT);
    }
}
