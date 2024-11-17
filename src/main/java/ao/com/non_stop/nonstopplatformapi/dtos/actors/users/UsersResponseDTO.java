package ao.com.non_stop.nonstopplatformapi.dtos.actors.users;

import java.util.Date;

public record UsersResponseDTO(String id, String name, String email, String password, Date created_at) {
}
