package cn.uuxl.dawn.service.impl;

import cn.uuxl.dawn.dal.entity.User;
import cn.uuxl.dawn.dal.mapper.UserMapper;
import cn.uuxl.dawn.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers() {
        logger.info("业务层：获取所有用户信息");
        return userMapper.selectAll();
    }

    public User getUserById(Long id) {
        logger.info("业务层：根据ID查询用户，ID: {}", id);
        return userMapper.selectById(id);
    }
}