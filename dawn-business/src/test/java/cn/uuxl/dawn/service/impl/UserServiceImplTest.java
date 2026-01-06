package cn.uuxl.dawn.service.impl;

import cn.uuxl.dawn.dal.entity.User;
import cn.uuxl.dawn.dal.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers_shouldReturnUsersFromMapper() {
        when(userMapper.selectAll()).thenReturn(List.of(new User(1L, "张三", "a@example.com")));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("张三");
    }

    @Test
    void getUserById_shouldDelegateToMapper() {
        User user = new User(1L, "张三", "a@example.com");
        when(userMapper.selectById(1L)).thenReturn(user);

        User result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("a@example.com");
    }
}