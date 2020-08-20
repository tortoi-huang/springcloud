package org.huang.cloud2.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/rest")
	public String cfgName1() {
		final List<ServiceInstance> instances = discoveryClient.getInstances("service-producer");
		final ServiceInstance instance = instances.get(0);
		return restTemplate.getForObject(instance.getUri() + "/rest/string", String.class);
	}

	@GetMapping("/cfg")
	public Map<String,Object> cfg() {
		Map<String,Object> map = new LinkedHashMap<>();
		final List<ServiceInstance> instances = discoveryClient.getInstances("service-producer");
		map.put("discoveryClient.getInstances",instances);
		final ServiceInstance instance = instances.get(0);
		map.put("instance.getUri()",instance.getUri());
		final String config = restTemplate.getForObject(instance.getUri() + "/rest/string", String.class);
		map.put("restTemplate.getForObject1",config);

		return map;
	}
}
