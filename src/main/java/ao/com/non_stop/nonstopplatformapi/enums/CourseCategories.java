package ao.com.non_stop.nonstopplatformapi.enums;

public enum CourseCategories {
    PROGRAMMING("PROGRAMMING"),
    BACKEND("BACKEND"),
    DESIGN("DESIGN"),
    FRONTEND("FRONTEND");

    private String courseCategories;

    private CourseCategories(String courseCategories){
        this.courseCategories = courseCategories;
    }
}
