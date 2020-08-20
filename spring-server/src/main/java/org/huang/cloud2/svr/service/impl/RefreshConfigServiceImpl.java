package org.huang.cloud2.svr.service.impl;

import org.huang.cloud2.svr.service.RefreshConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class RefreshConfigServiceImpl implements RefreshConfigService {

	@Value("${config.name}")
	private String name;

	@Override
	public String getName() {
		return name;
	}
}
