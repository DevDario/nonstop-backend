package ao.com.non_stop.nonstopplatformapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class NonstopplatformapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonstopplatformapiApplication.class, args);
	}

}
