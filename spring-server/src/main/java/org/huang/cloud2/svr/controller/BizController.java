package org.huang.cloud2.svr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizController {

	/**
	 * 用于测试hystrix抛出异常的情况，根据 unit调整异常发生的纪录 1-1_000_000_000之间，概率线性增大
	 * @param unit 除数
	 * @return 余数
	 */
	@GetMapping("/remain/{unit}")
	public int getRemain(@PathVariable int unit) {
		int ret = 1_000_000_000 % unit;
		if(ret == 0) {
			throw new RuntimeException("zero");
		}
		return ret;
	}
}
