package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Admin Service
 **/
@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * add
     */
    public void add(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException("The admin already exists");
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword("admin");
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    /**
     * update
     */
    public void updateById(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * select
     */
    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }

    /**
     * selectPage
     */
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * login
     */
    public Account login(Account account) {
        Account dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException("The admin does not exist");
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException("The username or password is incorrect");
        }
        return dbAdmin;
    }

    /**
     * updatePassword
     */
    public void updatePassword(Account account) {
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException("The admin does not exist");
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException("The password is incorrect");
        }
        dbAdmin.setPassword(account.getNewPassword());
        adminMapper.updateById(dbAdmin);
    }

}