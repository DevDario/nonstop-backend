package ao.com.non_stop.nonstopplatformapi.domain.entities;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "enrollment_date", nullable = false)
    private Date enrollment_date;

    @Min(0)
    @Max(100)
    @Column(name = "progress_percentage", nullable = false)
    private Float progress;

    @Column(name = "is_completed", nullable = false)
    private Boolean is_completed = false;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
