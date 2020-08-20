package org.huang.cloud2.balance;

import org.huang.cloud2.common.ShareBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalanceController {
	private static Logger logger = LoggerFactory.getLogger(BalanceController.class);
	private final String SERVICE_NAME = "service-producer";
	private final String SERVICE_URL = "http://service-producer";

	/**
	 * 这个 RestTemplate 实例是经过 @LoadBalanced 代理的实例
	 */
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancerClient loadBalancer;

	@GetMapping("/json")
	public ShareBean cfgJson() {
		//这个 RestTemplate 实例是经过 @LoadBalanced 代理的实例，自动实现负载均衡，可以根据url分析出服务真实地址
		return restTemplate.getForObject(SERVICE_URL + "/rest/json", ShareBean.class);
	}

	@GetMapping("/json2")
	public ShareBean cfgJson2() {
		//获取一个服务实例地址
		final String url = loadBalancer.choose(SERVICE_NAME).getUri().toString();
		logger.info("url: {}",url);
		//新建的 RestTemplate 没有负载均衡能力，只能访问真是的地址
		final RestTemplate template = new RestTemplate();
		return template.getForObject(url + "/rest/json", ShareBean.class);
	}
}
