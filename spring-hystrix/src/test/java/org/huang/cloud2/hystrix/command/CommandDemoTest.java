package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandDemoTest {
    private HystrixRequestContext context;

    /**
     * 测试没有熔断
     */
    @Test
    public void testExecute() {
        final int totalSize = 10_000;
        for(int i = 0; i < totalSize;i++) {
            System.out.println(new CommandDemo( i,3).execute());
        }
        final int wellSize = Config.PATH_COUNT.getOrDefault("s", 0);
        final int failSize = Config.PATH_COUNT.getOrDefault("f", 0);
        final int errorSize = Config.PATH_COUNT.getOrDefault("e", 0);

        assertEquals(failSize, errorSize);
        assertEquals(3334, errorSize);
        assertEquals(totalSize - errorSize, wellSize);
    }

    /**
     * 测试发生熔断
     */
    @Test
    public void testExecute2() {
        final int totalSize = 10_000;
        for(int i = 0; i < totalSize;i++) {
            System.out.println(new CommandDemo(i, 2).execute());
        }
        final int wellSize = Config.PATH_COUNT.getOrDefault("s", 0);
        final int failSize = Config.PATH_COUNT.getOrDefault("f", 0);
        final int errorSize = Config.PATH_COUNT.getOrDefault("e", 0);

        System.out.println(Config.PATH_COUNT);
        assertTrue(failSize > errorSize);
        assertTrue( wellSize < totalSize / 2 + 1);
    }


    @Before
    public void setup() {
        Config.PATH_COUNT.clear();
        context = HystrixRequestContext.initializeContext();
    }

    @After
    public void tearDown() {
        context.shutdown();
    }
}