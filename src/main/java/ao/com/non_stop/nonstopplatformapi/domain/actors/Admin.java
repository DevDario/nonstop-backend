package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "admins")
@Builder
@AllArgsConstructor
public class Admin extends User{

    @UpdateTimestamp
    @Column(nullable = false)
    private Date last_login;

    public Admin(){
        this.setRole(Roles.ADMIN);
    }
}
