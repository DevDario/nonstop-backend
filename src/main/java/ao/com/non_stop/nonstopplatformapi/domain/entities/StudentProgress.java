package ao.com.non_stop.nonstopplatformapi.domain.entities;

import ao.com.non_stop.nonstopplatformapi.domain.actors.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "student_progress")
@Builder
@Getter
@Setter
public class StudentProgress {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id",nullable = false)
    private CourseClasses courseClass;

    @Column(nullable = false)
    private Boolean watched;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date last_watched_at;
}