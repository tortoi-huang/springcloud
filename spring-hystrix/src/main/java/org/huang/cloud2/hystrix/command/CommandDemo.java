package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.*;

import static org.huang.cloud2.hystrix.command.Config.PATH_COUNT;
import static org.huang.cloud2.hystrix.command.Config.semaphoreSetting;

/**
 * 通过传入一个小数来计算失败的概率并手动抛出异常.
 */
public class CommandDemo extends HystrixCommand<Integer> {
	private final int main;
	private final int rate;

	/**
	 * 测试熔断类
	 * @param name 名称标记
	 * @param main
	 * @param rate 失败概率
	 */
	public CommandDemo(int main, int rate) {
		super(semaphoreSetting());
		this.main = main;
		this.rate = rate;
	}

	@Override
	protected Integer run() {
		int remain = main % rate;
		if(remain == 0) {
			PATH_COUNT.compute("e",(k,v) -> v == null ? 1 : v + 1);
			throw new RuntimeException("not work this time");
		}
		PATH_COUNT.compute("s",(k,v) -> v == null ? 1 : v + 1);
		return remain;
	}

	@Override
	protected Integer getFallback() {
		PATH_COUNT.compute("f",(k,v) -> v == null ? 1 : v + 1);
		return -1;
	}
}
