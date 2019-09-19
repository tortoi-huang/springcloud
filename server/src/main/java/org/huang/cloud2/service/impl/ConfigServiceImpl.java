package org.huang.cloud2.service.impl;

import org.huang.cloud2.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Value("${config.name}")
	private String name;

	@Override
	public String getName() {
		return name;
	}
}
