package ao.com.non_stop.nonstopplatformapi.dtos.auth;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;

public record RegisterRequestDTO(String name, String email,String about_me, String specialization ,Roles role, String password) {
}
