package cn.uuxl.dawn.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class VirtualThreadConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadConfig.class);

    private ExecutorService executorService;

    /**
     * 提供单例的虚拟线程执行器，Spring 关闭时自动 close，避免泄露。
     */
    @Bean
    public ExecutorService virtualThreadPerTaskExecutor() {
        logger.info("初始化虚拟线程执行器");
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
        return this.executorService;
    }

    /**
     * 优雅关闭虚拟线程执行器
     */
    @PreDestroy
    public void shutdownExecutor() {
        if (executorService != null && !executorService.isShutdown()) {
            logger.info("开始关闭虚拟线程执行器...");
            executorService.shutdown();
            try {
                // 等待最多 10 秒让任务完成
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    logger.warn("虚拟线程执行器未在 10 秒内关闭，强制关闭");
                    executorService.shutdownNow();
                } else {
                    logger.info("虚拟线程执行器已优雅关闭");
                }
            } catch (InterruptedException e) {
                logger.warn("等待虚拟线程执行器关闭时被中断", e);
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 为Tomcat配置虚拟线程执行器，复用单例，避免重复创建。
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        logger.info("为Tomcat配置虚拟线程执行器");

        return protocolHandler -> protocolHandler.setExecutor(virtualThreadPerTaskExecutor());
    }

    /**
     * 为@Async异步任务配置虚拟线程执行器，复用同一实例。
     */
    @Override
    public Executor getAsyncExecutor() {
        logger.info("为@Async异步任务配置虚拟线程执行器");

        ExecutorService executor = virtualThreadPerTaskExecutor();
        return new TaskExecutorAdapter(executor);
    }
}