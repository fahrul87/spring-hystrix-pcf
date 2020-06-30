package com.fahrul.springhystrixpcf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
public class SpringHystrixPcfApplication {
	
	@Autowired
	private RestTemplate template;
	
	@GetMapping("/")
	public String getAPIResponse() {
		return template.getForObject("http://dummy.restapiexample.com/api/v1/employees", String.class);
	}
	
	@GetMapping("/api")
	@HystrixCommand(fallbackMethod = "apiFallBack")
	public String getFailureAPI() {
		return template.getForObject("http://nourlfound.com/", String.class);
	}
	
	
	private String apiFallBack() {
		return "API is not working , try again latter..";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringHystrixPcfApplication.class, args);
	}
	
	@Bean
	public RestTemplate template() {
		return new 	RestTemplate();
	}

}
