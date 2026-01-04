package cn.uuxl.dawn.http.controller;

import cn.uuxl.dawn.dal.entity.User;
import cn.uuxl.dawn.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController() {
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("HTTP层：接收到获取所有用户请求");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        logger.info("HTTP层：接收到查询用户请求，ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}