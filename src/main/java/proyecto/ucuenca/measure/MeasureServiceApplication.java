package proyecto.ucuenca.measure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MeasureServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasureServiceApplication.class, args);
	}

}
