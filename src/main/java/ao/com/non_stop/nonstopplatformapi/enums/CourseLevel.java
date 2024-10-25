package ao.com.non_stop.nonstopplatformapi.enums;

public enum CourseLevel {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    MEDIUM("MEDIUM"),
    ADVANCED("ADVANCED");

    private String courseLevel;

    private CourseLevel(String courseLevel){
        this.courseLevel = courseLevel;
    }
}
