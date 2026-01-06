package cn.uuxl.dawn.service.config;

import cn.uuxl.dawn.service.BusinessServiceScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {BusinessServiceScanner.class})
public class BusinessServiceConfig {
}
