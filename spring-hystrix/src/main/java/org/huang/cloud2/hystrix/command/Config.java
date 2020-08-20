package org.huang.cloud2.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Config {
    public static final Map<String,Integer> PATH_COUNT = new ConcurrentHashMap<>();

    public static HystrixCommand.Setter semaphoreSetting() {
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")) //创建一个setter对象并设置分组名词
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandDemo")) //设置为此类调用设置一个名称，可选
                //.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")) // 设置一个专用的隔离线程池
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(500)  // 设置超时时间
                        .withCircuitBreakerRequestVolumeThreshold(10) // 至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerSleepWindowInMilliseconds(5000) //熔断器熔断后5秒后会进入半打开状态
                        .withCircuitBreakerErrorThresholdPercentage(40) //错误率达到50开启熔断保护
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)// 使用信号量隔离资源
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(11) //信号量隔离最大并发请求数
                );
    }
}
