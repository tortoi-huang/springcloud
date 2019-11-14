package org.huang.cloud2.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HystrixController {
	private static final Logger log = LoggerFactory.getLogger(HystrixController.class);
	@Autowired
	private ServiceWrapper serviceWrapper;

	@GetMapping("/sleep")
	public List<Long> invokeSleep() {
		int size = 1000;
		int base = 2;
		List<Long> list = new ArrayList<>();
		for(int i = 0;i < size;i++) {
			log.info("sleep: base {}", base);
			list.add(serviceWrapper.randomTimeout(base));
			log.info("sleep: {} --------------------------------", base);
		}
		return list;
	}
}
