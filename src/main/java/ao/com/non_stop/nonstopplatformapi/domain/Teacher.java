package ao.com.non_stop.nonstopplatformapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher extends User{

    @Column(name = "about_me", nullable = false, length = 110)
    private String about_me;

    @Column(name = "specialization")
    private String specialization;
}
