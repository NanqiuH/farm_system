package com.example.service;

import com.example.entity.Goods;
import com.example.entity.GoodsStock;
import com.example.mapper.GoodsStockMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GoodsStock Service
 **/
@Service
public class GoodsStockService {

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * add
     */
    @Transactional
    public void add(GoodsStock goodsStock) {
        int num = goodsStock.getNum();
        Goods goods = goodsService.selectById(goodsStock.getGoodsId());
        goods.setStock(goods.getStock() + num);
        goodsService.updateById(goods); // update
        goodsStockMapper.insert(goodsStock);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        goodsStockMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(GoodsStock goodsStock) {
        goodsStockMapper.updateById(goodsStock);
    }

    /**
     * select
     */
    public GoodsStock selectById(Integer id) {
        return goodsStockMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<GoodsStock> selectAll(GoodsStock goodsStock) {
        return goodsStockMapper.selectAll(goodsStock);
    }

    /**
     * selectPage
     */
    public PageInfo<GoodsStock> selectPage(GoodsStock goodsStock, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsStock> list = goodsStockMapper.selectAll(goodsStock);
        return PageInfo.of(list);
    }
}