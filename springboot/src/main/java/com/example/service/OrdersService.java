package com.example.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.exception.CustomException;
import com.example.mapper.OrdersMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Orders Service
 **/
@Service
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * add
     */
    @Transactional
    public void add(Orders orders) {
        orders.setOrderNo(IdUtil.fastSimpleUUID()); // unique order number
        orders.setTime(DateUtil.now());
        orders.setStatus("Awaiting payment");

        Goods goods = goodsService.selectById(orders.getGoodsId());
        if(goods == null){
            throw new CustomException("The products does not exist");
        }
        int store = goods.getStock() - orders.getNum();
        if(store < 0){
            throw new CustomException("Insufficient stock of goods");
        }
        goods.setStock(store);
        goodsService.updateById(goods);
        ordersMapper.insert(orders);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        ordersMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(Orders orders) {
        if("Canceled".equals(orders.getStatus())){
            Integer goodsId = orders.getGoodsId();
            Goods goods = goodsService.selectById(goodsId);
            if(goods != null) {
                goods.setStock(goods.getStock() + orders.getNum());
                goodsService.updateById(goods);
            }
        }
        ordersMapper.updateById(orders);
    }

    /**
     * select
     */
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    /**
     * selectPage
     */
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = ordersMapper.selectAll(orders);
        return PageInfo.of(list);
    }
}