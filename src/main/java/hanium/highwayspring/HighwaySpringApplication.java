package hanium.highwayspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HighwaySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighwaySpringApplication.class, args);
	}

}
