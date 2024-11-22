package ao.com.non_stop.nonstopplatformapi.domain.actors;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


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

    @NotNull(message = "You need to provide a Password for your account")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private String created_at;

    @Column(nullable = false)
    private String updated_at;

    @PrePersist
    protected void onCreate(){
        created_at = updated_at = java.time.LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate(){
        updated_at = java.time.LocalDateTime.now().toString();
    }
}
