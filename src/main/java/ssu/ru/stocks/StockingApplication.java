package ssu.ru.stocks;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class StockingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockingApplication.class, args);
	}

	@Bean
	@Scope("prototype")
	public Random random() {
		return new Random();
	}
}
