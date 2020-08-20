package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过传入一个小数来计算失败的概率并手动抛出异常.
 */
public class CommandHelloWorld extends HystrixCommand<String> {
	public static final Map<String,Integer> PATH_COUNT = new ConcurrentHashMap<>();
	private final String name;
	private final float rate;

	/**
	 * 测试熔断类
	 * @param name 名称标记
	 * @param rate 失败概率
	 */
	public CommandHelloWorld(String name, float rate) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")) //创建一个setter对象并设置分组名词
				//.andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld")) //设置为此类调用设置一个名称，可选
				//.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")) // 设置一个专用的隔离线程池
				//.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)) // 设置超时时间
		);
		this.name = name;
		this.rate = rate > 1 || rate < 1 ? 0 : 1;
	}

	@Override
	protected String run() {
		if(Math.random() < rate) {
			PATH_COUNT.compute("e",(k,v) -> v == null ? 1 : v + 1);
			throw new RuntimeException("not work this time");
		}
		PATH_COUNT.compute("s",(k,v) -> v == null ? 1 : v + 1);
		return "Hello " + name + "!";
	}

	@Override
	protected String getFallback() {
		PATH_COUNT.compute("f",(k,v) -> v == null ? 1 : v + 1);
		return "good bye " + name + ".";
	}
}
