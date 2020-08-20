package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CommandHelloWorldTest {
	private HystrixRequestContext context;

	@Test
	public void testExecute() throws Exception {
		assertEquals("Hello World!", new CommandHelloWorld("World",0f).execute());
		assertEquals("Hello Bob!", new CommandHelloWorld("Bob",0f).queue().get());

		assertEquals("good bye World.", new CommandHelloWorld("World",1f).execute());
		assertEquals("good bye Bob.", new CommandHelloWorld("Bob",1f).queue().get());
	}

	@Test
	public void testBreak() {
		float rate = 0.5f;
		int totalCount = 5000;
		for (int i = 0; i < totalCount; i++) {
			new CommandHelloWorld("huang", rate).execute();
		}
		System.out.println(CommandHelloWorld.PATH_COUNT);
		final Integer quitFailCount = CommandHelloWorld.PATH_COUNT.get("f");
		final Integer failCount = CommandHelloWorld.PATH_COUNT.get("e");
		final Integer successCount = CommandHelloWorld.PATH_COUNT.get("s");
		assertNotNull(failCount);
		assertTrue(quitFailCount > failCount);
		assertTrue(quitFailCount > successCount);
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