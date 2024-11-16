package ao.com.non_stop.nonstopplatformapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_classes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class CourseClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    //in minutes
    @Column(name = "duration", nullable = false)
    private Long duration;

    //represents the order of the current content on the course content list
    @Column(nullable = false)
    private Long order;

    @Column(name = "content_url", nullable = false)
    private String content_url;

    //checks if the content(video/file/other) was fully consumed
    @Column(name = "is_completed", nullable = false)
    private Boolean is_completed;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "course_id", nullable = false)
    private Long course_id;
}
