package org.huang.cloud2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Cloud1Application {

	public static void main(String[] args) {
		SpringApplication.run(Cloud1Application.class, args);
	}

}
