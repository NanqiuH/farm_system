package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.exception.CustomException;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Category Service
 **/
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * add
     */
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    /**
     * select
     */
    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    /**
     * selectPage
     */
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}