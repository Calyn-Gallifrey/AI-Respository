package com.demo.carddemo.mapper;

import com.demo.carddemo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id, name, role, branch, avatar_url AS avatarUrl FROM user LIMIT 1")
    User findCurrentUser();
}
