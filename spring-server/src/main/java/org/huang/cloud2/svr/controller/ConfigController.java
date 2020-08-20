package org.huang.cloud2.svr.controller;

import org.huang.cloud2.common.ShareBean;
import org.huang.cloud2.svr.service.ConfigService;
import org.huang.cloud2.svr.service.RefreshConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/cfg")
public class ConfigController {
	private final ConfigService configService;
	private final RefreshConfigService refreshConfigService;

	public ConfigController(ConfigService configService, RefreshConfigService refreshConfigService) {
		this.configService = configService;
		this.refreshConfigService = refreshConfigService;
	}

	@GetMapping("/name")
	public String cfgName() {
		return "init name: " + configService.getName() + ", real name: " + refreshConfigService.getName();
	}
}
