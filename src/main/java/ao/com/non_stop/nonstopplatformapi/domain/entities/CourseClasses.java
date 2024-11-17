package ao.com.non_stop.nonstopplatformapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "You need to inform the title of this Class")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String title;

    //in minutes
    @Column(name = "duration", nullable = false)
    private Long duration;

    //represents the order of the current content on the course content list
    @Column(nullable = false, name = "content_order")
    private Long content_order;

    @NotBlank(message = "You need to inform this content url")
    @Column(name = "content_url", nullable = false)
    private String content_url;

    //checks if the content(video/file/other) was fully consumed
    @Column(name = "is_completed", nullable = false)
    private Boolean is_completed;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
