package com.atcx.service.impl;

import com.atcx.mapper.UserMapper;
import com.atcx.pojo.User;
import com.atcx.service.UserService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        int insert = userMapper.insert(user);
        return insert;
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateById(user);
        return i;
    }

    @Override
    public int deleteUser(int userId) {
        int i = userMapper.deleteById(userId);
        return i;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (queryPageBean.getQueryString() != null&&queryPageBean.getQueryString().trim().length()!=0){
            //有查询条件就添加查询条件
            wrapper.eq("accountname",queryPageBean.getQueryString());
        }
        //4.设置降序排序
//        wrapper.orderByDesc("id");
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        System.out.println(userPage.getRecords());
        PageResult pageResult = new PageResult(userPage.getTotal(), userPage.getRecords());
        return pageResult;
    }

    @Override
    public User getOneUserByUP(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername()).eq("password",user.getPassword());
        User selectOne = userMapper.selectOne(wrapper);
        return selectOne;
    }

    @Override
    public User getUserById(int UserId) {
        User user = userMapper.selectById(UserId);
        return user;
    }
}
