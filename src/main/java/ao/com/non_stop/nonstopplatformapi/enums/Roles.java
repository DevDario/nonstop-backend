package ao.com.non_stop.nonstopplatformapi.enums;

public enum Roles {
    ADMIN("ADMIN"),
    TEACHER("TEACHER"),
    STUDENT("STUDENT"),
    USER("USER");

    private String roles;

    private Roles(String roles){
        this.roles = roles;
    }
}
