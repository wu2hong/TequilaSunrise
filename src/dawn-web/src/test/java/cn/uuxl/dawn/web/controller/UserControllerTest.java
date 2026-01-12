package cn.uuxl.dawn.web.controller;

import cn.uuxl.dawn.dal.entity.User;
import cn.uuxl.dawn.web.aop.ControllerExceptionAdvice;
import cn.uuxl.dawn.web.config.VirtualThreadConfig;
import cn.uuxl.dawn.web.model.UserConverter;
import cn.uuxl.dawn.web.model.UserDto;
import cn.uuxl.dawn.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserControllerTest.TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {

    @TestConfiguration
    @Import({VirtualThreadConfig.class, ControllerExceptionAdvice.class})
    @ComponentScan(basePackages = {"cn.uuxl.dawn.web.controller", "cn.uuxl.dawn.web.model"},
            excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "cn\\.uuxl\\.dawn\\.(dal|service)\\..*"))
    static class TestConfig {
        // 测试专用配置，不加载 DawnApplication
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter userConverter;

    @Test
    void getAllUsers_shouldReturnWrappedDtoList() throws Exception {
        User user = new User(1L, "张三", "a@example.com");
        UserDto dto = new UserDto(1L, "张三", "a@example.com");
        when(userService.getAllUsers()).thenReturn(List.of(user));
        when(userConverter.toDtoList(List.of(user))).thenReturn(List.of(dto));

        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data[0].name").value("张三"));
    }
}