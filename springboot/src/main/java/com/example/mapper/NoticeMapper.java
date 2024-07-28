package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * NoticeMapper
*/
public interface NoticeMapper {

    /**
      * insert
    */
    int insert(Notice notice);

    /**
      * delete
    */
    @Delete("delete from notice where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(Notice notice);

    /**
      * select
    */
    @Select("select * from notice where id = #{id}")
    Notice selectById(Integer id);

    /**
      * selectAll
    */
    List<Notice> selectAll(Notice notice);
}