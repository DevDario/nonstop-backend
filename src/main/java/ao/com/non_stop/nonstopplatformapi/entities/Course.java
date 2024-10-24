package ao.com.non_stop.nonstopplatformapi.entities;

import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Timer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date releaseDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<CourseCategory> categories;

    @Column
    private String image;
}
