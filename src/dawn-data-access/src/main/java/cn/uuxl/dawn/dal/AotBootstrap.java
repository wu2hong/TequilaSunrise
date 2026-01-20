package cn.uuxl.dawn.dal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AOT 引导类
 * 仅用于 AOT 原生镜像构建时的元数据处理，不用于实际运行
 */
@SpringBootApplication(scanBasePackages = "cn.uuxl.dawn.dal")
public class AotBootstrap {
    public static void main(String[] args) {
        // 此方法仅用于 AOT 处理，不会实际执行
        SpringApplication.run(AotBootstrap.class, args);
    }
}
