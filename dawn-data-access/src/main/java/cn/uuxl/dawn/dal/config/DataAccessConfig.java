package cn.uuxl.dawn.dal.config;

import cn.uuxl.dawn.dal.DataAccessScanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackageClasses = {DataAccessScanner.class})
public class DataAccessConfig {
}
