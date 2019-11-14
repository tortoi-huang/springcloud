package org.huang.cloud2.hystrix.command;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncCommandHelloWorldTest {

	@Test
	public void testSubscribe() {
		ObjContainer<String> container1 = new ObjContainer<>();
		ObjContainer<String> container2 = new ObjContainer<>();
		CountDownLatch count = new CountDownLatch(2);
		final Observable<String> wellDown = new AsyncCommandHelloWorld("huang", 0f).observe();
		wellDown.subscribe(s -> {container1.set(s); count.countDown();});

		final Observable<String> fail = new AsyncCommandHelloWorld("huang", 0.99999f).observe();
		fail.subscribe(s -> {container2.set(s); count.countDown();});
		try {
			count.await(1000L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals("Hello huang!",container1.get());
		Assert.assertEquals("good bye huang.",container2.get());
	}
}