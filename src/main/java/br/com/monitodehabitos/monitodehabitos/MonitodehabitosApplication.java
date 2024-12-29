package br.com.monitodehabitos.monitodehabitos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MonitodehabitosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitodehabitosApplication.class, args);
	}

}
