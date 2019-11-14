package org.huang.cloud2.controller;

import org.huang.cloud2.common.ShareBean;
import org.huang.cloud2.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ConfigController {
	@Autowired
	private ConfigService configService;

	@GetMapping("/cfg_name")
	public String cfgName() {
		return configService.getName();
	}

	@GetMapping("/cfg_json")
	public ShareBean cfgJson() {
		return new ShareBean("huang",30,new Date());
	}
}
