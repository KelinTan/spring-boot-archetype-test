package com.github.kelin.archetype.mapper;

import com.github.kelin.archetype.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id,name FROM user WHERE id = #{id}")
    User getUserById(Long id);

    @Insert("INSERT INTO user(name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);
}