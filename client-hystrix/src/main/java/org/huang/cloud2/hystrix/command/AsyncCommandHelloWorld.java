package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.schedulers.Schedulers;

public class AsyncCommandHelloWorld extends HystrixObservableCommand<String> {
	private final String name;
	private final float rate;

	public AsyncCommandHelloWorld(String name, float rate) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
		this.rate = rate > 0 && rate < 1 ? rate : 0;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create((Observable.OnSubscribe<String>) observer -> {
			try {
				if (!observer.isUnsubscribed()) {
					if(Math.random() < rate) {
						throw new RuntimeException("not work this time");
					}
					// a real example would do work like a network call here
					observer.onNext("Hello");
					observer.onNext(name + "!");
					observer.onCompleted();
				}
			} catch (Exception e) {
				observer.onError(e);
			}
		}).subscribeOn(Schedulers.io());
	}
}
