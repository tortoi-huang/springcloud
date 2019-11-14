package org.huang.cloud2.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceWrapper {
	private static final Logger log = LoggerFactory.getLogger(ServiceWrapper.class);
	private static final Long TIME_OUT = 500L;

	@HystrixCommand(fallbackMethod = "sleepTimeErr",
			commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")})
	public long randomTimeout(float base) {
		long sleepTime = (long)(TIME_OUT * base * Math.random());
		log.info("sleep: start {}", sleepTime);
		try {
			Thread.sleep(sleepTime);
			log.info("sleep: {}",sleepTime);
		} catch (InterruptedException e) {
			//超时会中断线程
			log.info("sleep: exception");
		}
		return sleepTime;
	}

	public long sleepTimeErr(float base) {
		log.info("sleep: error");
		return 0;
	}
}
