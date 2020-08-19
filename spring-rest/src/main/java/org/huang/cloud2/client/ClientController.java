package org.huang.cloud2.client;

import org.huang.cloud2.common.ShareBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {
	@Autowired
	private LoadBalancerClient loadBalancer;
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/cfg_name1")
	public String cfgName1() {
		final List<ServiceInstance> instances = discoveryClient.getInstances("service-producer");
		final ServiceInstance instance = instances.get(0);
		return restTemplate.getForObject(instance.getUri() + "/cfg_name", String.class);
	}

	@GetMapping("/cfg_name2")
	public String cfgName2() {
		return invoke("/cfg_name", String.class);
	}

	@GetMapping("/cfg_json")
	public ShareBean cfgJson() {
		return invoke("/cfg_json", ShareBean.class);
	}

	@GetMapping("/cfg")
	public Map<String,Object> cfg() {
		Map<String,Object> map = new LinkedHashMap<>();
		final List<ServiceInstance> instances = discoveryClient.getInstances("service-producer");
		map.put("discoveryClient.getInstances",instances);
		final ServiceInstance instance = instances.get(0);
		map.put("instance.getUri()",instance.getUri());
		final String config = restTemplate.getForObject(instance.getUri() + "/cfg_name", String.class);
		map.put("restTemplate.getForObject1",config);

		final String url = loadBalancer.choose("service-producer").getUri().toString();
		map.put("loadBalancer.choose",url);
		final String config2 = restTemplate.getForObject(url + "/cfg_name", String.class);
		map.put("restTemplate.getForObject2",config2);

		return map;
	}

	private <T> T invoke(String uri,Class<T> clazz) {
		final String url = loadBalancer.choose("service-producer").getUri().toString();
		return restTemplate.getForObject(url + uri, clazz);
	}
}
