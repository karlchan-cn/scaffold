package com.scaffold.service.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务调度配置
 *
 * @author 80275131
 * @version 1.0
 * @date 2020/7/23 13:57
 * @since 1.0
 **/
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfig implements AsyncConfigurer {

	@Bean(destroyMethod = "shutdown", value = "taskExecutor")
	public ThreadPoolTaskExecutor executorService() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(15);
		executor.setQueueCapacity(100);
		executor.setKeepAliveSeconds(60);
		executor.setThreadNamePrefix("Async-Task-Service-");

		// 线程池对拒绝任务的处理策略,直接丢弃
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

		//shutdown 执行时等待任务执行完成
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(1);
		// 初始化
		executor.initialize();
		return executor;
	}

	@Override
	public Executor getAsyncExecutor() {
		return executorService();
	}
}
