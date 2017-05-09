package com.softisland;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
@ImportResource("classpath:applicationContext.xml")
public class AppController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 SpringApplication.run(AppController.class, args);
	}

}
