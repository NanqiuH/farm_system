package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * OrdersMapper
*/
public interface OrdersMapper {

    /**
      * insert
    */
    int insert(Orders orders);

    /**
      * delete
    */
    @Delete("delete from orders where id = #{id}")
    int deleteById(Integer id);

    /**
      * update
    */
    int updateById(Orders orders);

    /**
      * select
    */
    @Select("select * from orders where id = #{id}")
    Orders selectById(Integer id);

    /**
      * selectAll
    */
    List<Orders> selectAll(Orders orders);

}