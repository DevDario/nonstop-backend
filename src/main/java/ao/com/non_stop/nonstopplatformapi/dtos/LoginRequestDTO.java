package ao.com.non_stop.nonstopplatformapi.dtos;

import ao.com.non_stop.nonstopplatformapi.enums.Roles;

public record LoginRequestDTO(String email, String password, Roles role) {
}
