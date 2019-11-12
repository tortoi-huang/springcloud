package org.huang.cloud2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizController {

	@GetMapping("/getRemain/{unit}")
	public int getRemain(@PathVariable int unit) {
		int ret = Integer.MAX_VALUE % unit;
		if(ret == 0) {
			throw new RuntimeException("zero");
		}
		return ret;
	}
}
