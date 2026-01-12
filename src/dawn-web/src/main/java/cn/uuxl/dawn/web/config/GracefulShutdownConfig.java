package cn.uuxl.dawn.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * 优雅关闭监听器
 * 监听 Spring 上下文关闭事件，记录关闭日志
 */
@Component
public class GracefulShutdownConfig implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdownConfig.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Spring 应用上下文开始关闭，执行优雅关闭流程...");
        // Spring Boot 的优雅关闭已经通过配置自动处理
        // 这里主要用于记录日志和监控
    }
}
