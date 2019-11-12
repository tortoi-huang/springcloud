package org.huang.cloud2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "helle consul";
	}

	@GetMapping("/hi")
	public String hi() {
		return "hi consul";
	}
}
