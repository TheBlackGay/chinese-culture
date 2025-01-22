package com.chinese.culture.admin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Data
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "app.thread-pool")
public class ThreadPoolConfig {

    private int coreSize;
    private int maxSize;
    private int queueCapacity;
    private int keepAliveSeconds;
    private String threadNamePrefix;

    @Bean("asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(coreSize);
        // 最大线程数
        executor.setMaxPoolSize(maxSize);
        // 队列容量
        executor.setQueueCapacity(queueCapacity);
        // 线程活跃时间（秒）
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程名称前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间（秒）
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
