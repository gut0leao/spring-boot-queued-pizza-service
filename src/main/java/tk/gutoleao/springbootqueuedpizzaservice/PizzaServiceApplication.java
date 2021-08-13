package tk.gutoleao.springbootqueuedpizzaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PizzaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaServiceApplication.class, args);
	}

}
