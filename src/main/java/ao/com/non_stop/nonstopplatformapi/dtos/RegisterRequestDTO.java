package ao.com.non_stop.nonstopplatformapi.dtos;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;

public record RegisterRequestDTO(String name, String email, Roles role, String password) {
}
