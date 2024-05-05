package tukorea.projectlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class ProjectlinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectlinkApplication.class, args);
    }
}
