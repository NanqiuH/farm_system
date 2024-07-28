package com.example.mapper;

import com.example.entity.Goods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * GoodsMapper
*/
public interface GoodsMapper {

    /**
      * insert
    */
    int insert(Goods goods);

    /**
      * delete
    */
    @Delete("delete from goods where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(Goods goods);

    /**
      * select
    */
    @Select("select * from goods where id = #{id}")
    Goods selectById(Integer id);

    /**
      * selectAll
    */
    List<Goods> selectAll(Goods goods);


}