package com.example.springboot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//			String pid = String.valueOf(ProcessHandle.current().pid());
//			File attachFile = new File(".attach_pid" + pid);
//			if(attachFile.exists()) {
//				attachFile.delete();
//			}
//		}));
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void init()
	{
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}	
}
