package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * CategoryMapper
*/
public interface CategoryMapper {

    /**
      * insert
    */
    int insert(Category category);

    /**
      * delete
    */
    @Delete("delete from category where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(Category category);

    /**
      * select
    */
    @Select("select * from category where id = #{id}")
    Category selectById(Integer id);

    /**
      * selectAll
    */
    List<Category> selectAll(Category category);

}