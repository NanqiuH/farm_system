package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * UserMapper
*/
public interface UserMapper {

    /**
      * insert
    */
    int insert(User user);

    /**
      * delete
    */
    @Delete("delete from user where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(User user);

    /**
      * select
    */
    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    /**
      * selectAll
    */
    List<User> selectAll(User user);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

}