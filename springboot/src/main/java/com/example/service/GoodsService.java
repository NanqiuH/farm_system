package com.example.service;

import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Goods Service
 **/
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * add
     */
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    /**
     * select
     */
    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    /**
     * selectPage
     */
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }
}