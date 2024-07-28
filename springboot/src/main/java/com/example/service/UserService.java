package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * add
     */
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException("The username is exist");
        }
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword("123");
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole("USER");
        userMapper.insert(user);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    /**
     * select
     */
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * selectPage
     */
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    /**
     * login
     */
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("The username does not exist");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("The username or password is incorrect");
        }
        return dbUser;
    }

    /**
     * updatePassword
     */
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("The username does not exist");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("The password is incorrect");
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

    /**
     * register
     */
    public void register(User user) {
        this.add(user);
    }
}