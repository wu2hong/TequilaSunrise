package cn.uuxl.dawn.dal.mapper;

import cn.uuxl.dawn.dal.config.DataAccessConfig;
import cn.uuxl.dawn.dal.entity.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = UserMapperIntegrationTest.TestConfig.class)
class UserMapperIntegrationTest {

    @Configuration
    @Import(DataAccessConfig.class)
    @MapperScan(basePackages = "cn.uuxl.dawn.dal.mapper")
    static class TestConfig {

        @Bean
        public DataSource dataSource() throws Exception {
            // 使用临时文件数据库，更可靠
            Path tempDb = Files.createTempFile("test-db-", ".db");
            tempDb.toFile().deleteOnExit();
            String url = "jdbc:sqlite:" + tempDb.toAbsolutePath();
            
            org.sqlite.SQLiteDataSource dataSource = new org.sqlite.SQLiteDataSource();
            dataSource.setUrl(url);
            
            // 立即初始化数据库
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("init.sql"));
            populator.setSeparator(";");
            DatabasePopulatorUtils.execute(populator, dataSource);
            
            return dataSource;
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
            return sessionFactory.getObject();
        }
    }

    @org.springframework.beans.factory.annotation.Autowired
    private UserMapper userMapper;

    @Test
    void selectAll_shouldReturnSeedUsers() {
        List<User> users = userMapper.selectAll();

        assertThat(users).isNotEmpty();
        assertThat(users).extracting(User::getName)
                .contains("张三", "李四", "王五");
    }

    @Test
    void selectById_shouldReturnSingleUser() {
        List<User> users = userMapper.selectAll();
        assertThat(users).isNotEmpty();

        Long firstId = users.get(0).getId();
        User user = userMapper.selectById(firstId);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(firstId);
    }
}

