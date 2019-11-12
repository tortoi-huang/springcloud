package org.huang.cloud2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

	@GetMapping("/getRemain/{unit}")
	public Map<Integer,String> getRemain(@PathVariable int unit) {
		Random r = new Random();
		Map<Integer,String> map = new HashMap<>((int)(100 / 0.75 )+ 1);
		for(int i = 0;i < 100;i++) {
			final int i1 = r.nextInt(unit);
			try {
				map.put(i1,invoke("/getRemain/" + i1, Integer.class) + "");
			} catch (Exception e) {
				e.printStackTrace();
				map.put(i1,"exception");
			}
		}
		return map;
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
