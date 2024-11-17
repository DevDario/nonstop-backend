package ao.com.non_stop.nonstopplatformapi.domain.actors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
}
