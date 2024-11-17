package ao.com.non_stop.nonstopplatformapi.domain.entities;

import ao.com.non_stop.nonstopplatformapi.enums.CourseCategory;
import ao.com.non_stop.nonstopplatformapi.enums.CourseLevel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Size(max = 120, message = "Description cannot exceed 120 characters")
    @Column(nullable = false, length = 120)
    private String description;

    @Column(nullable = false)
    private Date releaseDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Column(name = "image_url")
    private String imageUrl;

    //in minutes
    @Column(nullable = false)
    private Long duration;

    @NotBlank
    @Column(nullable = false)
    private String language;

    @NotBlank
    @Column(nullable = false)
    private Float rating = 3.0F;

    @OneToMany(mappedBy = "course_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CourseClasses> courseClasses;
}
