package cn.uuxl.dawn.service.api;

import cn.uuxl.dawn.dal.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Integer id);
}
