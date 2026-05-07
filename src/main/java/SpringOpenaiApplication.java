import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.efeerturk.spring_openai.chat"})
@EntityScan(basePackages = {"com.efeerturk.spring_openai.chat"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringOpenaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOpenaiApplication.class, args);
	}

}
