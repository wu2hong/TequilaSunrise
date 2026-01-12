package cn.uuxl.dawn.web.model;

import cn.uuxl.dawn.dal.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User 实体与 UserDto 之间的转换器。
 */
@Component
public class UserConverter {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserDto> toDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
