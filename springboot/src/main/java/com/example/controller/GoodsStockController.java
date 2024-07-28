package com.example.controller;

import com.example.common.Result;
import com.example.entity.GoodsStock;
import com.example.service.GoodsStockService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GoodsStockController
 **/
@RestController
@RequestMapping("/goodsStock")
public class GoodsStockController {

    @Resource
    private GoodsStockService goodsStockService;

    /**
     * add
     */
    @PostMapping("/add")
    public Result add(@RequestBody GoodsStock goodsStock) {
        goodsStockService.add(goodsStock);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        goodsStockService.deleteById(id);
        return Result.success();
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody GoodsStock goodsStock) {
        goodsStockService.updateById(goodsStock);
        return Result.success();
    }

    /**
     * select
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        GoodsStock goodsStock = goodsStockService.selectById(id);
        return Result.success(goodsStock);
    }

    /**
     * selectAll
     */
    @GetMapping("/selectAll")
    public Result selectAll(GoodsStock goodsStock) {
        List<GoodsStock> list = goodsStockService.selectAll(goodsStock);
        return Result.success(list);
    }

    /**
     * selectPage
     */
    @GetMapping("/selectPage")
    public Result selectPage(GoodsStock goodsStock,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<GoodsStock> page = goodsStockService.selectPage(goodsStock, pageNum, pageSize);
        return Result.success(page);
    }

}