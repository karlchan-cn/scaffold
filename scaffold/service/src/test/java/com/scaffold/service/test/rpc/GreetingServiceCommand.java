package com.scaffold.service.test.rpc;

import com.netflix.hystrix.*;
import com.scaffold.service.rpc.GreetingService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author 80166776
 * pdb程序化熔断command.
 */
public class GreetingServiceCommand extends HystrixCommand<String> {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceCommand.class);

    private static final HystrixCommandGroupKey COMMAND_GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("ThirdPartyAd");
    private static final HystrixThreadPoolKey THREAD_POOL_KEY = HystrixThreadPoolKey.Factory.asKey("ThirdPartyAdPool");
    private static final HystrixCommandProperties.Setter COMMAND_PROPERTIES = HystrixCommandProperties.Setter()
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) // 在单独的线程池中执行
            .withCircuitBreakerEnabled(true)            // 打开熔断器
            .withCircuitBreakerRequestVolumeThreshold(500) // (10s内)请求数达到多大才进行熔断计算 
            .withExecutionTimeoutInMilliseconds(40)      // 100ms超时
            .withCircuitBreakerSleepWindowInMilliseconds(10000)    // 熔断10s后开始探测
            .withCircuitBreakerErrorThresholdPercentage(60)       // 出错率高于该值则熔断
            .withMetricsRollingPercentileEnabled(false)
            .withExecutionIsolationSemaphoreMaxConcurrentRequests(256)
            .withFallbackIsolationSemaphoreMaxConcurrentRequests(256)
            .withRequestLogEnabled(false)
            .withRequestCacheEnabled(false);
    private static final HystrixThreadPoolProperties.Setter THREAD_POOL_PROPERTIES = HystrixThreadPoolProperties.Setter()
            .withCoreSize(64)
            .withMaximumSize(128)
            .withAllowMaximumSizeToDivergeFromCoreSize(true);

    private final GreetingService greetingService;

    public GreetingServiceCommand(GreetingService greetingService) {
        super(Setter.withGroupKey(COMMAND_GROUP_KEY)
                .andCommandKey(HystrixCommandKey.Factory.asKey("greetingService"))
                .andThreadPoolKey(THREAD_POOL_KEY)
                .andCommandPropertiesDefaults(COMMAND_PROPERTIES)
                .andThreadPoolPropertiesDefaults(THREAD_POOL_PROPERTIES));
        this.greetingService = greetingService;

    }

    @Override
    protected String run() {
        greetingService.greetingWithOneWord();
        return (String) RpcContext.getContext().getCompletableFuture().orTimeout(200, TimeUnit.MILLISECONDS).join();
    }

    @Override
    protected String getFallback() {
        // 1.熔断
        if (this.circuitBreaker.isOpen()) {
            LOGGER.warn("circuitBreaker for commandKey:{}", commandKey);
            return null;
        }

        // 2.超时
        if (this.isCommandTimedOut.get().equals(TimedOutStatus.TIMED_OUT)) {
            LOGGER.warn("isCommandTimedOut for commandKey:{}", commandKey);
            return null;
        }
        // 3.线程池消耗完毕
        if (this.threadState.get().equals(ThreadState.NOT_USING_THREAD)) {
            LOGGER.warn("threadPool is consumed for commandKey:{}", commandKey);
            return null;
        }
        LOGGER.warn("unknown exception for commandKey:{}, ex is {}", commandKey, getFailedExecutionException());
        // 服务不可用/出异常/超时等情况时调用此方法
        return null;
    }

}