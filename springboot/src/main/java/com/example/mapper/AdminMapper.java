package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AdminMapper
*/
public interface AdminMapper {

    /**
      * insert
    */
    int insert(Admin admin);

    /**
      * delete
    */
    @Delete("delete from admin where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(Admin admin);

    /**
      * select
    */
    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);

    /**
      * selectAll
    */
    List<Admin> selectAll(Admin admin);

    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);

}