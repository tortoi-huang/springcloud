package org.huang.cloud2.controller;

import org.huang.cloud2.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
	@Autowired
	private ConfigService configService;

	@GetMapping("/cfg_name")
	public String cfgName() {
		return configService.getName();
	}
}
