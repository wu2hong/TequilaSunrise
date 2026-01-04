package cn.uuxl.dawn.dal.mapper;

import cn.uuxl.dawn.dal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    
    List<User> selectAll();
    
    User selectById(Integer id);
}