package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CommandHelloWorldTest {
	private HystrixRequestContext context;

	@Test
	public void testExecute() throws Exception {
		assertEquals("Hello World!", new CommandHelloWorld("World",0f).queue().get());
		assertEquals("Hello Bob!", new CommandHelloWorld("Bob",0f).queue().get());
	}

	@Test
	public void testBreak() {
		float rate = 0.5f;
		int totalCount = 5000;
		Map<String,Integer> map = new HashMap<>(3);
		for (int i = 0; i < totalCount; i++) {
			final char c = new CommandHelloWorld("huang", rate).execute().charAt(0);
			map.compute(String.valueOf(c),(k,v) -> v == null ? 1 : v + 1);
		}
		System.out.println(map);
		final Integer failCount = map.get("g");
		assertNotNull(failCount);
		//为了表示远大于实际概率
		assertTrue(failCount * 1.0 / totalCount > 1 - 0.5 * rate);
	}

	@Test
	public void testSubscribe() {
		ObjContainer<String> container1 = new ObjContainer<>();
		ObjContainer<String> container2 = new ObjContainer<>();
		CountDownLatch count = new CountDownLatch(2);
		final Observable<String> wellDown = new CommandHelloWorld("huang", 0f).observe();
		wellDown.subscribe(s -> {container1.set(s); count.countDown();});

		final Observable<String> fail = new CommandHelloWorld("huang", 0.99999f).observe();
		fail.subscribe(s -> {container2.set(s); count.countDown();});
		try {
			count.await(1000L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Hello huang!",container1.get());
		assertEquals("good bye huang.",container2.get());
	}

	@Before
	public void setup() {
		context = HystrixRequestContext.initializeContext();
	}

	@After
	public void tearDown() {
		context.shutdown();
	}
}