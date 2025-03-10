package br.com.chain.workflow_processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
public class WorkflowProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowProcessorApplication.class, args);
	}

}
