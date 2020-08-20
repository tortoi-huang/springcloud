package org.huang.cloud2.svr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Cloud2SvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cloud2SvrApplication.class, args);
	}

}
