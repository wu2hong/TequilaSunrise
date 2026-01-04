package cn.uuxl.dawn.http;

import cn.uuxl.dawn.dal.config.DataAccessConfig;
import cn.uuxl.dawn.service.config.BusinessServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BusinessServiceConfig.class, DataAccessConfig.class})
public class DawnApplication {
    public static void main(String[] args) {
        SpringApplication.run(DawnApplication.class, args);
    }
}