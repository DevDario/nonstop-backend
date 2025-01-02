package ao.com.non_stop.nonstopplatformapi.dtos.actors.admin;

import ao.com.non_stop.nonstopplatformapi.domain.entities.Course;

import java.util.List;

public record AdminsResponseDTO(String id, String name, String email, String created_at, String updated_at) {
}