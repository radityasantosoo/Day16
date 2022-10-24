package com.codeflex.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.codeflex.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined

public class SpringbootRestOnlineStore {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestOnlineStore.class, args);
	}
}
