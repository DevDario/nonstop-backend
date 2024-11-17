package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 8, max = 12, message = "Password must have at least 8 charachters")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;
}
