package ao.com.non_stop.nonstopplatformapi.enums;

public enum CourseCategory {
    MICROSSERVICE("MICROSSERVICE"),
    BACKEND("BACKEND"),
    DESIGN("DESIGN"),
    FRONTEND("FRONTEND"),
    API("API"),
    WEB("WEB"),
    MOBILE("MOBILE"),
    IOT("IOT"),
    APP_DEVELOPMENT("APP DEVELOPMENT"),
    DESKTOP_DEVELOPMENT("DESKTOP DEVELOPMENT"),
    UX("UX");

    private String courseCategory;

    private CourseCategory(String courseCategory){
        this.courseCategory = courseCategory;
    }
}
