package com.scaffold.service.hystrix;

import com.netflix.hystrix.*;
import com.scaffold.service.rpc.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 80166776
 * pdb程序化熔断command.
 */
@Slf4j
public class HystrixCommand extends com.netflix.hystrix.HystrixCommand<String> {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixCommand.class);

    private static final HystrixCommandGroupKey COMMAND_GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("HystrixGroupKey");
    private static final HystrixThreadPoolKey THREAD_POOL_KEY = HystrixThreadPoolKey.Factory.asKey("HystrixThreadPool");
    private static final HystrixCommandProperties.Setter COMMAND_PROPERTIES = HystrixCommandProperties.Setter()
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) // 在单独的线程池中执行
            .withCircuitBreakerEnabled(true)            // 打开熔断器
            .withCircuitBreakerRequestVolumeThreshold(10) // (10s内)请求数达到多大才进行熔断计算
            .withExecutionTimeoutInMilliseconds(10000)      // 1s超时
            .withCircuitBreakerSleepWindowInMilliseconds(10000)    // 熔断10s后开始探测
            .withCircuitBreakerErrorThresholdPercentage(60)       // 出错率高于该值则熔断
            .withMetricsRollingPercentileEnabled(false)
            .withRequestLogEnabled(false)
            .withRequestCacheEnabled(false);
    private static final HystrixThreadPoolProperties.Setter THREAD_POOL_PROPERTIES = HystrixThreadPoolProperties.Setter()
            .withCoreSize(1)
            .withMaximumSize(1)
            .withMaxQueueSize(100000)
            .withAllowMaximumSizeToDivergeFromCoreSize(true);

    public HystrixCommand() {
        super(Setter.withGroupKey(COMMAND_GROUP_KEY)
                .andCommandKey(HystrixCommandKey.Factory.asKey("hystrixCommand"))
                .andThreadPoolKey(THREAD_POOL_KEY)
                .andCommandPropertiesDefaults(COMMAND_PROPERTIES)
                .andThreadPoolPropertiesDefaults(THREAD_POOL_PROPERTIES));

    }

    private static final AtomicInteger EXECUTE_COUNT = new AtomicInteger();
    private static final AtomicInteger EXECUTE_COUNT_SUCCESS = new AtomicInteger();
    private static final AtomicInteger FALL_BACK_COUNT = new AtomicInteger();

    /**
     * 返回执行数量.
     *
     * @return 执行数量
     */
    public static final int queryExecuteCount() {
        return EXECUTE_COUNT.get();
    }

    /**
     * 返回执行数量.
     *
     * @return 执行数量
     */
    public static final int queryExecuteSuccessCount() {
        return EXECUTE_COUNT_SUCCESS.get();
    }

    /**
     * 返回fallback数量.
     *
     * @return fallback数量.
     */
    public static final int queryFallBackCount() {
        return FALL_BACK_COUNT.get();
    }

    @Override
    protected String run() {
        System.out.println("run start");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            log.error("sleep error.", e);
            return "error";
        }
        return "success";
    }

    @Override
    protected String getFallback() {
        FALL_BACK_COUNT.incrementAndGet();
        // 1.熔断
        if (this.circuitBreaker.isOpen()) {
            LOGGER.warn("circuitBreaker for commandKey:{}", commandKey);
            System.err.println("circuitBreaker for commandKey:" + commandKey);
            return null;
        }

        // 2.超时
        if (this.isCommandTimedOut.get().equals(TimedOutStatus.TIMED_OUT)) {
            LOGGER.warn("isCommandTimedOut for commandKey:{}", commandKey);
            System.err.println("isCommandTimedOut for commandKey:" + commandKey);
            return null;
        }
        // 3.线程池消耗完毕
        if (this.threadState.get().equals(ThreadState.NOT_USING_THREAD)) {
            LOGGER.warn("threadPool is consumed for commandKey:{}", commandKey);
            System.err.println("threadPool is consumed for commandKey:" + commandKey);
            return null;
        }
        LOGGER.warn("unknown exception for commandKey:{}, ex is {}", commandKey, getFailedExecutionException());
        System.err.println("unknown exception for commandKey:" + commandKey);
        // 服务不可用/出异常/超时等情况时调用此方法
        return null;
    }

}