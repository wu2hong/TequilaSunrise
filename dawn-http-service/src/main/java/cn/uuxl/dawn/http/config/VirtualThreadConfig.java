package cn.uuxl.dawn.http.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class VirtualThreadConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadConfig.class);


    /**
     * 为Tomcat配置虚拟线程执行器 [2,4](@ref)
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        logger.info("为Tomcat配置虚拟线程执行器");

        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    /**
     * 为@Async异步任务配置虚拟线程执行器 [2,3](@ref)
     */
    @Override
    public java.util.concurrent.Executor getAsyncExecutor() {
        logger.info("为@Async异步任务配置虚拟线程执行器");

        return new TaskExecutorAdapter(
                Executors.newThreadPerTaskExecutor(
                        Thread.ofVirtual().name("virtual-async-", 1).factory()
                )
        );
    }
}