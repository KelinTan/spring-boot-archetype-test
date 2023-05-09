package com.github.kelin.archetype.mapper;

import com.github.kelin.archetype.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserV2Mapper {
    @Select("SELECT id,name FROM user_v2 WHERE id = #{id}")
    User getUserById(Long id);

    @Insert("INSERT INTO user_v2(name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);
}