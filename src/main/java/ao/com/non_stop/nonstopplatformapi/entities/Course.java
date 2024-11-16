package ao.com.non_stop.nonstopplatformapi.entities;

import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private CourseCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Column
    private String image;

    //in minutes
    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Float rating;

    @OneToMany(mappedBy = "course_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CourseClasses> classes;
}
