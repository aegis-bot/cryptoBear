package com.trade.cryptoBear;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import com.trade.cryptoBear.entity.TraderAccount;
import com.trade.cryptoBear.repository.TraderAccountRepository;
import com.trade.cryptoBear.repository.UserRepository;

@SpringBootApplication
@RestController
@EnableScheduling
@ComponentScan(basePackages= "com")
public class CryptoBearApplication {

    public static void main(String[] args) {
      SpringApplication.run(CryptoBearApplication.class, args);
    }

	@Autowired
	TraderAccountRepository traderRepository;

	@Bean 
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		TraderAccount defaultUser = new TraderAccount();
		defaultUser.setUsername("warren buffet");
		defaultUser.setBalance(new BigDecimal(50000));
		traderRepository.save(defaultUser);

		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				//System.out.println(beanName);
			}
		};
	}

	
}
