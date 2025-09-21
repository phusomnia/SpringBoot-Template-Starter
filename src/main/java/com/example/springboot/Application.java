package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			String pid = String.valueOf(ProcessHandle.current().pid());
			File attachFile = new File(".attach_pid" + pid);
			if(attachFile.exists()) {
				attachFile.delete();
			}
		}));
		SpringApplication.run(Application.class, args);
	}

}
