package cn.uuxl.dawn.web.controller;

import cn.uuxl.dawn.dal.entity.User;
import cn.uuxl.dawn.web.model.ApiResponse;
import cn.uuxl.dawn.web.model.UserConverter;
import cn.uuxl.dawn.web.model.UserDto;
import cn.uuxl.dawn.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        logger.info("HTTP层：接收到获取所有用户请求");
        List<User> users = userService.getAllUsers();
        List<UserDto> dtoList = userConverter.toDtoList(users);
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        logger.info("HTTP层：接收到查询用户请求，ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            UserDto dto = userConverter.toDto(user);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.failure(404, "用户不存在"));
        }
    }
}