package org.huang.cloud2.balance;

import org.huang.cloud2.common.ShareBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalanceController {
	private final String SERVICE_NAME = "http://service-producer";

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/cfg_json")
	public ShareBean cfgJson() {
		return invoke("/cfg_json", ShareBean.class);
	}

	private <T> T invoke(String uri,Class<T> clazz) {
		return restTemplate.getForObject(SERVICE_NAME + uri, clazz);
	}
}
