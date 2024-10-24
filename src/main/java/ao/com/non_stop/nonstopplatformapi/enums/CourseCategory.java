package ao.com.non_stop.nonstopplatformapi.enums;

public enum CourseCategory {
    PROGRAMMING("PROGRAMMING"),
    BACKEND("BACKEND"),
    DESIGN("DESIGN"),
    FRONTEND("FRONTEND");

    private String courseCategory;

    private CourseCategory(String courseCategory){
        this.courseCategory = courseCategory;
    }
}
