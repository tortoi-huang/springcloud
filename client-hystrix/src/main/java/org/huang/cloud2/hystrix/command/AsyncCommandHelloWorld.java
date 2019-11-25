package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class AsyncCommandHelloWorld extends HystrixObservableCommand<String> {
	public static final Map<String,Integer> PATH_COUNT = new HashMap<>();
	private final String name;
	private final float rate;

	public AsyncCommandHelloWorld(String name, float rate) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
		this.rate = rate > 0 && rate < 1 ? rate : 0;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.unsafeCreate(observer -> {
			try {
				if (!observer.isUnsubscribed()) {
					if(Math.random() < rate) {
						PATH_COUNT.compute("e",(k,v) -> v == null ? 1 : v + 1);
						throw new RuntimeException("not work this time");
					}
					PATH_COUNT.compute("s",(k,v) -> v == null ? 1 : v + 1);
					observer.onNext("Hello " + name + "!");
					observer.onCompleted();
				}
			} catch (Exception e) {
				observer.onError(e);
			}
		});
	}
}
