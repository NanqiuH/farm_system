package com.example.mapper;

import com.example.entity.GoodsStock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * GoodsStockMapper
*/
public interface GoodsStockMapper {

    /**
      * insert
    */
    int insert(GoodsStock goodsStock);

    /**
      * delete
    */
    @Delete("delete from goods_stock where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(GoodsStock goodsStock);

    /**
      * select
    */
    @Select("select * from goods_stock where id = #{id}")
    GoodsStock selectById(Integer id);

    /**
      * selectAll
    */
    List<GoodsStock> selectAll(GoodsStock goodsStock);

}