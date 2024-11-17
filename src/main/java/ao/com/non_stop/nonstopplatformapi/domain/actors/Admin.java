package ao.com.non_stop.nonstopplatformapi.domain.actors;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table
@Builder
@AllArgsConstructor
public class Admin extends User{
}
